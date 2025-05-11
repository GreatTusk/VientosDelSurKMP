package com.portafolio.vientosdelsur.controller.room.di

import com.portafolio.vientosdelsur.service.housekeeping.di.RoomServiceModule
import org.koin.dsl.module

val RoomControllerModule = module { includes(RoomServiceModule) }