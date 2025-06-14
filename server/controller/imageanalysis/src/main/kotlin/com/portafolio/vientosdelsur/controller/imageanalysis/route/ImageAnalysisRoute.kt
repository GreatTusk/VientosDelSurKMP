package com.portafolio.vientosdelsur.controller.imageanalysis.route

import com.f776.core.common.onEmpty
import com.f776.core.common.onError
import com.f776.core.common.onSuccess
import com.portafolio.vientosdelsur.core.controller.util.isImage
import com.portafolio.vientosdelsur.core.controller.util.parseDateFromQueryParams
import com.portafolio.vientosdelsur.service.imageanalysis.ImageAnalysisService
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.utils.io.*
import org.koin.ktor.ext.inject
import kotlin.properties.Delegates

fun Application.imageAnalysisRoute() {
    val imageAnalysisService by inject<ImageAnalysisService>()

    routing {
        swaggerUI(path = "swagger/image-analysis", swaggerFile = "openapi/documentation-imageanalysis.yaml")
        route("/image-analysis") {
            post {
                val maxFileSize = 5 * 1024 * 1024 // 5MB
                lateinit var photoBytes: ByteArray
                var roomId by Delegates.notNull<Int>()

                try {
                    val multipart = call.receiveMultipart()
                    multipart.forEachPart { part ->
                        when (part) {
                            is PartData.FormItem -> {
                                if (part.name == "room-id") {
                                    val id = part.value.toIntOrNull() ?: run {
                                        part.dispose()
                                        return@forEachPart call.respond(
                                            HttpStatusCode.BadRequest
                                        )
                                    }
                                    roomId = id
                                }
                            }

                            is PartData.FileItem -> {
                                log.debug("Multipart had a file!")
                                if (part.name == "room-photo") {
                                    log.debug("Multipart was room-photo!")
                                    val contentType = part.contentType
                                    if (contentType?.contentType == "image") {
                                        log.debug("Multipart was an image!")
                                        val bytes = part.provider().toByteArray()
                                        log.debug("Received bytes!")

                                        check(bytes.size <= maxFileSize) { "Image was too large" }

                                        log.debug("Passed size check!")
                                        check(bytes.isImage()) { "Was not an image" }
                                        photoBytes = bytes

                                    }
                                }
                                part.dispose()
                            }

                            else -> part.dispose()
                        }
                    }
                } catch (e: Exception) {
                    log.error("Error processing multipart request for new employee", e)
                    return@post call.respond(HttpStatusCode.BadRequest, "Invalid multipart request")
                }

                imageAnalysisService.analyze(photoBytes, roomId)
                    .onSuccess {
                        call.respond(it)
                    }
                    .onError {
                        log.error(it.name)
                        call.respond(HttpStatusCode.InternalServerError)
                    }
            }

            get("/{id}") {
                val imageAnalysisId =
                    call.pathParameters["id"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest)

                imageAnalysisService.serveImage(analysisId = imageAnalysisId)
                    .onSuccess { imageBytes ->
                        call.respondBytes(
                            bytes = imageBytes,
                            contentType = ContentType.Image.JPEG
                        )
                    }
                    .onError {
                        call.respond(HttpStatusCode.InternalServerError, "Something happened: $it")
                    }
                    .onEmpty {
                        call.respond(HttpStatusCode.NotFound)
                    }
            }

            get("/taken-on") {
                val date = call.parseDateFromQueryParams() ?: return@get call.respond(
                    HttpStatusCode.BadRequest,
                    "Missing or invalid date query parameter"
                )
                imageAnalysisService.getImageAnalysisTakenOn(date)
                    .onSuccess { call.respond(it) }
                    .onError {
                        log.error(it.name)
                        call.respond(HttpStatusCode.InternalServerError)
                    }
                    .onEmpty { call.respond(HttpStatusCode.NotFound) }
            }

            get("/room/{roomId}/on") {
                val roomId = call.parameters["roomId"]?.toIntOrNull() ?: return@get call.respond(
                    HttpStatusCode.BadRequest,
                    "Invalid room ID"
                )
                val date = call.parseDateFromQueryParams() ?: return@get call.respond(
                    HttpStatusCode.BadRequest,
                    "Missing or invalid date query parameter"
                )
                imageAnalysisService.getImageAnalysisFromRoomOn(roomId, date)
                    .onSuccess { call.respond(it) }
                    .onError {
                        log.error(it.name)
                        call.respond(HttpStatusCode.InternalServerError)
                    }
                    .onEmpty { call.respond(HttpStatusCode.NotFound) }
            }

            get("/approved/on") {
                val date = call.parseDateFromQueryParams() ?: return@get call.respond(
                    HttpStatusCode.BadRequest,
                    "Missing or invalid date query parameter"
                )
                imageAnalysisService.getApprovedAnalysisOn(date)
                    .onSuccess { call.respond(it) }
                    .onError {
                        log.error(it.name)
                        call.respond(HttpStatusCode.InternalServerError)
                    }
                    .onEmpty { call.respond(HttpStatusCode.NotFound) }
            }

            get("/disapproved/on") {
                val date = call.parseDateFromQueryParams() ?: return@get call.respond(
                    HttpStatusCode.BadRequest,
                    "Missing or invalid date query parameter"
                )
                imageAnalysisService.getDisapprovedAnalysisOn(date)
                    .onSuccess { call.respond(it) }
                    .onError {
                        log.error(it.name)
                        call.respond(HttpStatusCode.InternalServerError)
                    }
                    .onEmpty { call.respond(HttpStatusCode.NotFound) }
            }
        }
    }
}