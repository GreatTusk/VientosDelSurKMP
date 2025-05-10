package com.portafolio.vientosdelsur.controller.room.di

import com.portafolio.vientosdelsur.data.room.di.RoomDataModule
import org.koin.dsl.module

val RoomControllerModule = module { includes(RoomDataModule) }