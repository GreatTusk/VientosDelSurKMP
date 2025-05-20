package com.portafolio.vientosdelsur.controller.employee.route

import com.f776.core.common.onEmpty
import com.f776.core.common.onError
import com.f776.core.common.onSuccess
import com.portafolio.vientosdelsur.service.employee.EmployeeService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.swagger.*
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
                        call.respond(HttpStatusCode.NotFound, "Nothing")
                    }
            }
        }
    }
}