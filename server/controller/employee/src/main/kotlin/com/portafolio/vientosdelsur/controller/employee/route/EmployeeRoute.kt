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
                var isValidImage = false

                multipart.forEachPart { part ->
                    when (part) {
                        is PartData.FileItem -> {
                            // Check content type from part metadata
                            val contentType = part.contentType
                            if (contentType != null && contentType.contentType == "image") {
                                imageBytes = part.provider().toByteArray()

                                // Validate image by checking magic numbers
                                if (imageBytes!!.isNotEmpty()) {
                                    isValidImage = when {
                                        // JPEG: starts with FF D8 FF
                                        imageBytes!!.size >= 3 &&
                                                imageBytes!![0] == 0xFF.toByte() &&
                                                imageBytes!![1] == 0xD8.toByte() &&
                                                imageBytes!![2] == 0xFF.toByte() -> true

                                        // PNG: starts with 89 50 4E 47
                                        imageBytes!!.size >= 4 &&
                                                imageBytes!![0] == 0x89.toByte() &&
                                                imageBytes!![1] == 0x50.toByte() &&
                                                imageBytes!![2] == 0x4E.toByte() &&
                                                imageBytes!![3] == 0x47.toByte() -> true

                                        else -> false
                                    }
                                }
                            }
                        }

                        else -> {}
                    }
                    part.dispose()
                }

                if (imageBytes == null || !isValidImage) {
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