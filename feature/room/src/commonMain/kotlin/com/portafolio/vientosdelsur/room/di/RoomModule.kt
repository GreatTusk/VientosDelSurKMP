package com.portafolio.vientosdelsur.room.di

import com.portafolio.vientosdelsur.room.screens.housekeeperForYou.HousekeeperForYouViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val RoomModule = module {
    viewModelOf(::HousekeeperForYouViewModel)
}