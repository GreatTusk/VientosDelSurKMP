package com.portafolio.vientosdelsur.data.shift.di

import com.portafolio.vientosdelsur.data.shift.repository.DbHousekeeperShiftRepository
import com.portafolio.vientosdelsur.data.shift.repository.DbShiftRepository
import com.portafolio.vientosdelsur.domain.housekeeping.HousekeeperShiftRepository
import com.portafolio.vientosdelsur.domain.shift.ShiftRepository
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val ShiftDataModule = module {
    single {
        DbShiftRepository(
            defaultDispatcher = get(named("defaultDispatcher"))
        )
    }.bind<ShiftRepository>()

    single {
        DbHousekeeperShiftRepository(
            defaultDispatcher = get(named("defaultDispatcher"))
        )
    }.bind<HousekeeperShiftRepository>()
}