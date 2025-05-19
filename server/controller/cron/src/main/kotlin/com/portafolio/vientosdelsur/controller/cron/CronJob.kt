package com.portafolio.vientosdelsur.controller.cron

import com.f776.core.common.onError
import com.f776.core.common.onSuccess
import com.portafolio.vientosdelsur.service.housekeeping.RoomDistributionService
import com.portafolio.vientosdelsur.service.shift.ShiftSchedulerService
import io.ktor.server.application.*
import kotlinx.coroutines.*
import kotlinx.datetime.*
import org.koin.ktor.ext.inject
import kotlin.time.Duration.Companion.milliseconds

fun Application.cronJob() {
    val shiftSchedulerService by inject<ShiftSchedulerService>()
    val roomDistributionService by inject<RoomDistributionService>()
    val log = environment.log

    val job = CoroutineScope(Dispatchers.IO).launch {
        while (isActive) {
            val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

            if (now.dayOfMonth == 1) {
                shiftSchedulerService.scheduleShifts()
                    .onSuccess {
                        roomDistributionService.distributeRoomsMonth(now.date).onError {
                            log.error("Something went wrong with room distribution $it")
                        }
                    }.onError {
                        log.error("Something went wrong with shift scheduling $it")
                    }
            }

            // Calculate time until first day of next month
            val nextMonth = if (now.month == Month.DECEMBER) {
                LocalDateTime(now.year + 1, Month.JANUARY, 1, 0, 0, 0)
            } else {
                LocalDateTime(now.year, now.month.next(), 1, 0, 0, 0)
            }

            val nowInstant = now.toInstant(TimeZone.currentSystemDefault())
            val nextMonthInstant = nextMonth.toInstant(TimeZone.currentSystemDefault())
            val delayMillis = (nextMonthInstant - nowInstant).inWholeMilliseconds

            log.info("Delaying for $delayMillis")
            delay(delayMillis.milliseconds)
        }
    }

    monitor.subscribe(ApplicationStopping) {
        job.cancel()
        log.info("Cron job canceled due to application stopping")
    }
}

// Extension function to get the next month
private fun Month.next(): Month {
    return Month.of(ordinal + 2)
}