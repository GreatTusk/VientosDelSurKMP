package com.portafolio.vientosdelsur.service.housekeeping.di

import com.portafolio.vientosdelsur.data.room.di.RoomDataModule
import com.portafolio.vientosdelsur.service.housekeeping.RoomService
import com.portafolio.vientosdelsur.service.housekeeping.RoomServiceImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val RoomServiceModule = module {
    includes(RoomDataModule)
    singleOf(::RoomServiceImpl).bind<RoomService>()
}