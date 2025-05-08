package com.portafolio.vientosdelsur.di

import com.portafolio.vientosdelsur.room.di.RoomModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val AppModule = module {
    includes(RoomModule)
    single(named("ioDispatcher")) { Dispatchers.IO }
}