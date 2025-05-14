package com.portafolio.vientosdelsur.room.di

import com.portafolio.vientosdelsur.data.room.di.RoomDataModule
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val RoomModule = module {
    includes(RoomDataModule)
    viewModelOf(::HousekeeperForYouViewModel)
}