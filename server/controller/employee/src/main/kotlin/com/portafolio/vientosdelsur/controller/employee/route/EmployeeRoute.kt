package com.portafolio.vientosdelsur.controller.employee.route

import com.f776.core.common.takeOrDefault
import com.portafolio.vientosdelsur.data.employee.repository.EmployeeRepository
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.employeeRoute() {
    val employeeRepository by inject<EmployeeRepository>()

    route("employee") {
        get {
            call.respond(employeeRepository.allEmployees().takeOrDefault(emptyList()))
        }
    }
}