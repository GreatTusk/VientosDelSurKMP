package com.portafolio.vientosdelsur.controller.employee.route

import com.f776.core.common.onEmpty
import com.f776.core.common.onError
import com.f776.core.common.onSuccess
import com.portafolio.vientosdelsur.data.employee.repository.EmployeeRepository
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.employeeRoute() {
    val employeeRepository by inject<EmployeeRepository>()

    routing {
        route("/employee") {
            get {
                employeeRepository.allEmployees()
                    .onSuccess {
                        call.respond(it)
                    }.onError {
                        call.respond("Something happened: $it")
                    }.onEmpty {
                        call.respond("Nothing")
                    }
            }
        }
    }
}