package com.portafolio.vientosdelsur.feature.hotel.di

import com.f776.japanesedictionary.data.imageanalysis.di.ImageAnalysisDataModule
import com.portafolio.vientosdelsur.data.room.di.RoomDataModule
import com.portafolio.vientosdelsur.feature.hotel.screens.roomanalysis.RoomAnalysisViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val HotelModule = module {
    includes(ImageAnalysisDataModule, RoomDataModule)
    viewModelOf(::RoomAnalysisViewModel)
}