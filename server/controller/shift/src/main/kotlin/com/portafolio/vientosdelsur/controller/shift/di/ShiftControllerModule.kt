package com.portafolio.vientosdelsur.controller.shift.di

import com.portafolio.vientosdelsur.service.shift.di.ShiftSchedulerServiceModule
import org.koin.dsl.module

val ShiftControllerModule = module {
    includes(ShiftSchedulerServiceModule)
}