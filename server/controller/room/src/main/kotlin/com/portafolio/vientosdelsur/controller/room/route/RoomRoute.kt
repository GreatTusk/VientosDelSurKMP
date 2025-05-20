package com.portafolio.vientosdelsur.controller.room.route

import com.f776.core.common.onEmpty
import com.f776.core.common.onError
import com.f776.core.common.onSuccess
import com.portafolio.vientosdelsur.controller.room.util.parseDateFromQueryParams
import com.portafolio.vientosdelsur.service.housekeeping.RoomDistributionService
import com.portafolio.vientosdelsur.service.housekeeping.RoomService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.datetime.*
import org.koin.ktor.ext.inject

fun Application.roomRoute() {
    val roomService by inject<RoomService>()
    val roomDistributionService by inject<RoomDistributionService>()

    routing {
        swaggerUI("swagger/room", swaggerFile = "openapi/documentation-room.yaml")
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

            route("/distribution") {
                get("/{housekeeperId}") {
                    val housekeeperId = call.parameters["housekeeperId"]?.toIntOrNull()
                        ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid housekeeper ID")

                    val date = try {
                        call.parseDateFromQueryParams()
                    } catch (e: Exception) {
                        return@get call.respond(HttpStatusCode.BadRequest, "Invalid date format")
                    }

                    roomService.getRoomDistributionForHousekeeperOn(
                        housekeeperId = housekeeperId,
                        date = date
                    ).onSuccess {
                        call.respond(it)
                    }.onEmpty {
                        call.respond(HttpStatusCode.NotFound, "No room distribution found")
                    }.onError {
                        call.respond(HttpStatusCode.InternalServerError, it)
                    }
                }

                get {
                    val date = try {
                        call.parseDateFromQueryParams()
                    } catch (e: Exception) {
                        return@get call.respond(HttpStatusCode.BadRequest, "Invalid date format")
                    }

                    roomService.getAllRoomStatusOn(
                        date = date
                    ).onSuccess {
                        call.respond(it)
                    }.onEmpty {
                        call.respond(HttpStatusCode.NotFound, "No room distribution found")
                    }.onError {
                        call.respond(HttpStatusCode.InternalServerError, it)
                    }
                }

                post("/generate") {
                    val date = try {
                        call.parseDateFromQueryParams()
                    } catch (e: Exception) {
                        return@post call.respond(HttpStatusCode.BadRequest, "Invalid date format")
                    }

                    roomDistributionService.distributeRoomsMonth(date).onSuccess {
                        call.respond(it)
                    }.onEmpty {
                        call.respond(HttpStatusCode.NotFound, "No data to work with")
                    }.onError {
                        call.respond(HttpStatusCode.InternalServerError, it)
                    }
                }
            }
        }
    }
}