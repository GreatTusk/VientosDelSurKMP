package com.portafolio.vientosdelsur.di

import com.portafolio.vientosdelsur.controller.employee.di.EmployeeControllerModule
import com.portafolio.vientosdelsur.controller.room.di.RoomControllerModule
import com.portafolio.vientosdelsur.controller.shift.di.ShiftControllerModule
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

val AppModule = module {
    includes(EmployeeControllerModule, RoomControllerModule, ShiftControllerModule)
    single(named("defaultDispatcher")) { Dispatchers.Default }
    single(named("ioDispatcher")) { Dispatchers.IO }
}