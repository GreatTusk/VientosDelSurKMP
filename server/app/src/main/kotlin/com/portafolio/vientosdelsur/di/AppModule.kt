package com.portafolio.vientosdelsur.di

import com.portafolio.vientosdelsur.controller.employee.di.EmployeeControllerModule
import org.koin.dsl.module

val AppModule = module {
    includes(EmployeeControllerModule)
}