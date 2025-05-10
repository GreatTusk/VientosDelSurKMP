package com.portafolio.vientosdelsur.controller.room.route

import com.f776.core.common.onEmpty
import com.f776.core.common.onError
import com.f776.core.common.onSuccess
import com.portafolio.vientosdelsur.data.room.repository.RoomRepository
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.roomRoute() {
    val roomRepository by inject<RoomRepository>()

    routing {
        route("/room") {
            get {
                roomRepository.getAllRooms()
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