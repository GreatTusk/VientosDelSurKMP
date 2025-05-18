package com.portafolio.vientosdelsur.data.room.di

import com.portafolio.vientosdelsur.data.room.repository.DBRoomBookingRepository
import com.portafolio.vientosdelsur.data.room.repository.DBRoomRepository
import com.portafolio.vientosdelsur.domain.room.RoomBookingRepository
import com.portafolio.vientosdelsur.domain.room.RoomRepository
import org.koin.dsl.bind
import org.koin.dsl.module

val RoomDataModule = module {
    single { DBRoomRepository }.bind<RoomRepository>()
    single { DBRoomBookingRepository }.bind<RoomBookingRepository>()
}