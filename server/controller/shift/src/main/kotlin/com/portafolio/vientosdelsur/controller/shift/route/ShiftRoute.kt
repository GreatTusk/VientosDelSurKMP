package com.portafolio.vientosdelsur.controller.shift.route

import com.f776.core.common.onEmpty
import com.f776.core.common.onError
import com.f776.core.common.onSuccess
import com.portafolio.vientosdelsur.core.controller.util.parseDateFromQueryParams
import com.portafolio.vientosdelsur.core.controller.util.today
import com.portafolio.vientosdelsur.service.shift.ShiftSchedulerService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.shiftRoute() {
    val shiftSchedulerService by inject<ShiftSchedulerService>()

    routing {
        swaggerUI("swagger/shift", swaggerFile = "openapi/documentation-shift.yaml")
        route("/shifts") {
            get("/employees-working") {
                val date = try {
                    call.parseDateFromQueryParams() ?: today()
                } catch (e: IllegalArgumentException) {
                    return@get call.respond(HttpStatusCode.BadRequest, "Invalid date format")
                }

                shiftSchedulerService.getEmployeesWorkingOn(date)
                    .onSuccess {
                        call.respond(it)
                    }
                    .onError {
                        call.respond(HttpStatusCode.InternalServerError, "Error retrieving employees: $it")
                    }
                    .onEmpty {
                        call.respond(HttpStatusCode.NotFound, "No employees on shift")
                    }
            }


            post("/distribute") {
                shiftSchedulerService.scheduleShifts()
                    .onSuccess {
                        call.respond(it)
                    }.onError {
                        call.respond(HttpStatusCode.InternalServerError, "Something happened: $it")
                    }
            }
        }
    }
}