package com.portafolio.vientosdelsur.controller.room.route

import com.f776.core.common.onEmpty
import com.f776.core.common.onError
import com.f776.core.common.onSuccess
import com.portafolio.vientosdelsur.service.housekeeping.RoomService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.ktor.ext.inject

fun Application.roomRoute() {
    val roomService by inject<RoomService>()

    routing {
        route("/room") {
            get {
                roomService.getAllRooms()
                    .onSuccess {
                        call.respond(it)
                    }.onError {
                        call.respond(HttpStatusCode.InternalServerError, "Something happened: $it")
                    }.onEmpty {
                        call.respond(HttpStatusCode.NotFound, "Nothing")
                    }
            }
        }

        route("/room-status/{housekeeperId}") {
            get {
                val id = call.parameters["housekeeperId"]?.toIntOrNull()
                    ?: return@get call.respond(HttpStatusCode.BadRequest)

                roomService.getRoomDistributionForHousekeeperOn(
                    housekeeperId = id,
                    date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
                ).onSuccess {
                    call.respond(it)
                }.onEmpty {
                    call.respond(HttpStatusCode.NotFound)
                }.onError {
                    call.respond(HttpStatusCode.InternalServerError, it)
                }
            }
        }
    }
}