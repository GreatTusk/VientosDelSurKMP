package com.portafolio.vientosdelsur.foryou.di

import com.portafolio.vientosdelsur.data.employee.di.EmployeeDataModule
import com.portafolio.vientosdelsur.data.room.di.RoomDataModule
import com.portafolio.vientosdelsur.foryou.screens.foryou.ForYouViewModel
import com.portafolio.vientosdelsur.foryou.screens.foryou.housekeeper.HousekeeperForYouViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val RoomModule = module {
    includes(RoomDataModule, EmployeeDataModule)
    viewModelOf(::ForYouViewModel)
    viewModelOf(::HousekeeperForYouViewModel)
}