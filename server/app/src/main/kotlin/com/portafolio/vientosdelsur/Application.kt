package com.portafolio.vientosdelsur

import com.portafolio.vientosdelsur.config.configureContentNegotiation
import com.portafolio.vientosdelsur.config.configureDbConnection
import com.portafolio.vientosdelsur.config.configureKoin
import com.portafolio.vientosdelsur.controller.employee.route.employeeRoute
import com.portafolio.vientosdelsur.controller.room.route.roomRoute
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

    employeeRoute()
    roomRoute()

    setupCronJob()

    routing {
        get("/") {
            call.respondText("Hey all!")
        }
    }
}

private fun Application.setupCronJob() {
    val job = CoroutineScope(Dispatchers.IO).launch {
        while (isActive) {
            val now = LocalDateTime.now()
            log.info("Cron job executed at $now")

            if (now.dayOfMonth == 1) {
                log.info("It's the first day of ${now.month} ${now.year}! Performing monthly tasks...")
                // First-day-of-month specific code here
            }

            // Calculate time until first day of next month
            val nextMonth = if (now.monthValue == 12) {
                now.withMonth(1).withYear(now.year + 1)
            } else {
                now.withMonth(now.monthValue + 1)
            }
            val firstDayNextMonth = nextMonth.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0)
            val delayMillis = Duration.between(now, firstDayNextMonth).toMillis()

            log.info("Delaying for $delayMillis")
            delay(delayMillis)
        }
    }

    monitor.subscribe(ApplicationStopping) {
        job.cancel()
        log.info("Cron job canceled due to application stopping")
    }
}