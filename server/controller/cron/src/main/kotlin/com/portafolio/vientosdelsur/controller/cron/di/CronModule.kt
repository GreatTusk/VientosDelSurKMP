package com.portafolio.vientosdelsur.controller.cron.di

import com.portafolio.vientosdelsur.service.housekeeping.di.RoomServiceModule
import com.portafolio.vientosdelsur.service.shift.di.ShiftSchedulerServiceModule
import org.koin.dsl.module

val CronModule = module {
    includes(RoomServiceModule, ShiftSchedulerServiceModule)
}