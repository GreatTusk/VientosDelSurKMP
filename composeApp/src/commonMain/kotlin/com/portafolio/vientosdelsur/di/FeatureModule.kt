package com.portafolio.vientosdelsur.di

import com.portafolio.vientosdelsur.foryou.di.ForYouModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.qualifier.named
import org.koin.dsl.module

val AppModule = module {
    includes(ForYouModule)
    single(named("ioDispatcher")) { Dispatchers.IO }
}