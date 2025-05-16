package com.portafolio.vientosdelsur.controller.shift.route

import com.f776.core.common.onError
import com.f776.core.common.onSuccess
import com.portafolio.vientosdelsur.service.shift.ShiftService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.shiftRoute() {
    val shiftService by inject<ShiftService>()

    routing {
        route("/shifts/distribute") {
            get {
                shiftService.scheduleShifts().onSuccess {
                    call.respond(it)
                }.onError {
                    call.respond(HttpStatusCode.InternalServerError, "Something happened: $it")
                }
            }
        }
    }
}