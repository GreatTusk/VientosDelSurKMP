package com.portafolio.vientosdelsur.feature.shift.di

import com.portafolio.vientosdelsur.data.shift.di.ShiftDataModule
import com.portafolio.vientosdelsur.feature.shift.screens.employee.ShiftViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val ShiftModule = module {
    includes(ShiftDataModule)
    viewModelOf(::ShiftViewModel)
}