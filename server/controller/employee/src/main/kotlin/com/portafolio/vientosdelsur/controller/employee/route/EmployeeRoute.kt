package com.portafolio.vientosdelsur.controller.employee.route

import com.f776.core.common.takeOrDefault
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
                call.respond(employeeRepository.allEmployees().takeOrDefault(emptyList()))
            }
        }
    }
}