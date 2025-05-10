package com.portafolio.vientosdelsur.data.room.di

import com.portafolio.vientosdelsur.data.room.repository.DBRoomRepository
import com.portafolio.vientosdelsur.data.room.repository.RoomRepository
import org.koin.dsl.bind
import org.koin.dsl.module

val RoomDataModule = module {
    single { DBRoomRepository }.bind<RoomRepository>()
}