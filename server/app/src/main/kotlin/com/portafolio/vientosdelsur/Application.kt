package com.portafolio.vientosdelsur

import com.portafolio.vientosdelsur.config.configureContentNegotiation
import com.portafolio.vientosdelsur.config.configureDbConnection
import com.portafolio.vientosdelsur.config.configureKoin
import com.portafolio.vientosdelsur.controller.cron.cronJob
import com.portafolio.vientosdelsur.controller.employee.route.employeeRoute
import com.portafolio.vientosdelsur.controller.room.route.roomRoute
import com.portafolio.vientosdelsur.controller.shift.route.shiftRoute
import com.portafolio.vientosdelsur.core.database.entity.SchemaCreation
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.*
import java.time.Duration
import java.time.LocalDateTime

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    configureKoin()
    configureContentNegotiation()
    configureDbConnection()

    SchemaCreation.initializeDatabase()

    employeeRoute()
    roomRoute()
    shiftRoute()
    cronJob()
}