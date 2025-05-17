package com.portafolio.vientosdelsur.controller.cron

import com.f776.core.common.onError
import com.f776.core.common.onSuccess
import com.portafolio.vientosdelsur.service.housekeeping.RoomDistributionService
import com.portafolio.vientosdelsur.service.shift.ShiftSchedulerService
import io.ktor.server.application.*
import kotlinx.coroutines.*
import org.koin.ktor.ext.inject
import java.time.Duration
import java.time.LocalDateTime

fun Application.cronJob() {
    val shiftSchedulerService by inject<ShiftSchedulerService>()
    val roomDistributionService by inject<RoomDistributionService>()

    val job = CoroutineScope(Dispatchers.IO).launch {
        while (isActive) {
            val now = LocalDateTime.now()

            if (now.dayOfMonth == 1) {
                shiftSchedulerService.scheduleShifts()
                    .onSuccess {
                        roomDistributionService.distributeRoomsMonth().onError {
                            log.error("Something went wrong with room distribution $it")
                        }
                    }.onError {
                        log.error("Something went wrong with shift scheduling $it")
                    }
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