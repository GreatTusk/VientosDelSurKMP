package com.portafolio.vientosdelsur.controller.employee.route

import com.f776.core.common.onEmpty
import com.f776.core.common.onError
import com.f776.core.common.onSuccess
import com.portafolio.vientosdelsur.service.employee.EmployeeService
import com.portafolio.vientosdelsur.shared.dto.employee.EmployeeDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
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
                    }.onError {
                        call.respond(HttpStatusCode.InternalServerError, "Something happened: $it")
                    }.onEmpty {
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
                    }.onError {
                        call.respond(HttpStatusCode.InternalServerError, "Something happened: $it")
                    }.onEmpty {
                        call.respond(HttpStatusCode.NotFound)
                    }
            }
        }
    }
}