package com.portafolio.vientosdelsur.controller.shift.di

import com.portafolio.vientosdelsur.service.shift.di.ShiftServiceModule
import org.koin.dsl.module

val ShiftControllerModule = module {
    includes(ShiftServiceModule)
}