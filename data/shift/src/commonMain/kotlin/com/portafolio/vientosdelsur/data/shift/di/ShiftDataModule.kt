package com.portafolio.vientosdelsur.data.shift.di

import com.portafolio.vientosdelsur.data.shift.network.KtorRemoteShiftDataSource
import com.portafolio.vientosdelsur.data.shift.network.RemoteShiftDataSource
import com.portafolio.vientosdelsur.data.shift.repository.ShiftRepositoryImpl
import com.portafolio.vientosdelsur.domain.shift.ShiftRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val ShiftDataModule = module {
    singleOf(::KtorRemoteShiftDataSource).bind<RemoteShiftDataSource>()
    singleOf(::ShiftRepositoryImpl).bind<ShiftRepository>()
}