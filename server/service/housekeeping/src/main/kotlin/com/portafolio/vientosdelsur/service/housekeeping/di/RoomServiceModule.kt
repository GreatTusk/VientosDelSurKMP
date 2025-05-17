package com.portafolio.vientosdelsur.service.housekeeping.di

import com.portafolio.vientosdelsur.data.room.di.RoomDataModule
import com.portafolio.vientosdelsur.domain.housekeeping.usecase.DistributeRoomsUseCase
import com.portafolio.vientosdelsur.service.housekeeping.RoomDistributionService
import com.portafolio.vientosdelsur.service.housekeeping.RoomDistributionServiceImpl
import com.portafolio.vientosdelsur.service.housekeeping.RoomService
import com.portafolio.vientosdelsur.service.housekeeping.RoomServiceImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val RoomServiceModule = module {
    includes(RoomDataModule)
    factoryOf(::DistributeRoomsUseCase)
    singleOf(::RoomDistributionServiceImpl).bind<RoomDistributionService>()
    singleOf(::RoomServiceImpl).bind<RoomService>()
}