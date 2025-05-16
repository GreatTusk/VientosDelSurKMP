package com.portafolio.vientosdelsur.di

import com.portafolio.vientosdelsur.controller.employee.di.EmployeeControllerModule
import com.portafolio.vientosdelsur.controller.room.di.RoomControllerModule
import com.portafolio.vientosdelsur.controller.shift.di.ShiftControllerModule
import org.koin.dsl.module

val AppModule = module {
    includes(EmployeeControllerModule, RoomControllerModule, ShiftControllerModule)
}