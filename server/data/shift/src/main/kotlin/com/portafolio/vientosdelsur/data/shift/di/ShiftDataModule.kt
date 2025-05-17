package com.portafolio.vientosdelsur.data.shift.di

import com.portafolio.vientosdelsur.data.shift.repository.DbShiftRepository
import com.portafolio.vientosdelsur.domain.shift.ShiftRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val ShiftDataModule = module {
    singleOf(::DbShiftRepository).bind<ShiftRepository>()
}