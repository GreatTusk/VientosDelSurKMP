package com.portafolio.vientosdelsur.di

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val AppModule = module {
    single(named("ioDispatcher")) { Dispatchers.IO }
}

//expect val PlatformModule: Module