package com.portafolio.vientosdelsur

import com.portafolio.vientosdelsur.config.configureKoin
import com.portafolio.vientosdelsur.controller.employee.route.employeeRoute
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    configureKoin()

    routing {
        get("/") {
            call.respondText("Hey all!")
        }

        employeeRoute()
    }
}