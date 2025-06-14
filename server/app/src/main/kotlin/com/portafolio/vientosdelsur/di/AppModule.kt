package com.portafolio.vientosdelsur.di

import com.portafolio.vientosdelsur.controller.employee.di.EmployeeControllerModule
import com.portafolio.vientosdelsur.controller.imageanalysis.di.ImageAnalysisControllerModule
import com.portafolio.vientosdelsur.controller.room.di.RoomControllerModule
import com.portafolio.vientosdelsur.controller.shift.di.ShiftControllerModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import org.koin.core.qualifier.named
import org.koin.dsl.module

val AppModule = module {
    includes(EmployeeControllerModule, RoomControllerModule, ShiftControllerModule, ImageAnalysisControllerModule)
    single(named("defaultDispatcher")) { Dispatchers.Default }
    single(named("ioDispatcher")) { Dispatchers.IO }
    single { CoroutineScope(Dispatchers.Default + SupervisorJob()) }
}