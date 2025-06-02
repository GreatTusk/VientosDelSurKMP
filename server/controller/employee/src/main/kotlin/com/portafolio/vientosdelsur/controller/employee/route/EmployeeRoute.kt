package com.portafolio.vientosdelsur.controller.employee.route

import com.f776.core.common.onEmpty
import com.f776.core.common.onError
import com.f776.core.common.onSuccess
import com.portafolio.vientosdelsur.service.employee.EmployeeService
import com.portafolio.vientosdelsur.shared.dto.employee.EmployeeDto
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.utils.io.*
import org.koin.ktor.ext.inject

fun Application.employeeRoute() {
    val employeeService by inject<EmployeeService>()

    routing {
        swaggerUI(path = "swagger/employee", swaggerFile = "openapi/documentation-employee.yaml")
        route("/employee") {
            get {
                employeeService.getAllEmployees()
                    .onSuccess {
                        call.respond(it)
                    }
                    .onError {
                        call.respond(HttpStatusCode.InternalServerError, "Something happened: $it")
                    }
                    .onEmpty {
                        call.respond(HttpStatusCode.NotFound)
                    }
            }

            post {
                val dto = try {
                    call.receive<EmployeeDto.Create>()
                } catch (_: ContentTransformationException) {
                    return@post call.respond(HttpStatusCode.BadRequest)
                } catch (_: Exception) {
                    return@post call.respond(HttpStatusCode.InternalServerError)
                }

                employeeService.createEmployee(dto)
                    .onSuccess {
                        call.respond(HttpStatusCode.Created)
                    }
                    .onError {
                        call.respond(HttpStatusCode.InternalServerError)
                    }
            }

            get("/active/{userId}") {
                val userId = call.parameters["userId"] ?: return@get call.respond(
                    HttpStatusCode.BadRequest,
                    "User ID is required"
                )

                employeeService.isEmployeeActive(userId)
                    .onSuccess {
                        call.respond(HttpStatusCode.OK)
                    }
                    .onError {
                        call.respond(HttpStatusCode.NotFound)
                    }
            }
        }

        route("/user") {
            get("/{id}") {
                val id = call.pathParameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid id")

                employeeService.getEmployeeByUserId(id)
                    .onSuccess {
                        call.respond(it)
                    }
                    .onError {
                        call.respond(HttpStatusCode.InternalServerError, "Something happened: $it")
                    }
                    .onEmpty {
                        call.respond(HttpStatusCode.NotFound)
                    }
            }

            get("/profile-picture/{id}") {
                val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid id")

                employeeService.getProfilePicture(id)
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

            put("/profile-picture/{id}") {
                val id = call.parameters["id"] ?: return@put call.respond(HttpStatusCode.BadRequest, "Invalid id")

                val multipart = call.receiveMultipart()
                var imageBytes: ByteArray? = null
                val maxFileSize = 5 * 1024 * 1024

                multipart.forEachPart { part ->
                    if (part is PartData.FileItem) {
                        val contentType = part.contentType
                        if (contentType?.contentType == "image") {
                            try {
                                val bytes = part.provider().toByteArray()

                                // Check file size first
                                if (bytes.size > maxFileSize) {
                                    part.dispose()
                                    return@forEachPart
                                }

                                if (bytes.isValidImage()) {
                                    imageBytes = bytes
                                }
                            } finally {
                                part.dispose()
                            }
                        }
                    } else {
                        part.dispose()
                    }
                }

                if (imageBytes == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid image format or no image found")
                    return@put
                }

                employeeService.updateProfilePicture(id, imageBytes!!)
                    .onSuccess {
                        call.respond(HttpStatusCode.OK, "Profile picture updated successfully")
                    }
                    .onError {
                        call.respond(HttpStatusCode.InternalServerError, "Something happened: $it")
                    }
            }
        }
    }
}

private fun ByteArray.isValidImage(): Boolean {
    return when {
        // JPEG signature
        size >= 3 && this[0] == 0xFF.toByte() && this[1] == 0xD8.toByte() && this[2] == 0xFF.toByte() -> true
        // PNG signature
        size >= 4 && this[0] == 0x89.toByte() && this[1] == 0x50.toByte() &&
                this[2] == 0x4E.toByte() && this[3] == 0x47.toByte() -> true

        else -> false
    }
}