package com.portafolio.vientosdelsur.data.housekeeping.di

import com.portafolio.vientosdelsur.data.housekeeping.repository.DbHousekeeperShiftRepository
import com.portafolio.vientosdelsur.domain.housekeeping.HousekeeperShiftRepository
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val HousekeepingDataModule = module {
    single {
        DbHousekeeperShiftRepository(
            defaultDispatcher = get(named("defaultDispatcher"))
        )
    }.bind<HousekeeperShiftRepository>()
}