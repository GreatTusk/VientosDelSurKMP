package com.portafolio.vientosdelsur.di

import com.portafolio.vientosdelsur.AppViewModel
import com.portafolio.vientosdelsur.feature.auth.di.AuthModule
import com.portafolio.vientosdelsur.foryou.di.ForYouModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val AppModule = module {
    includes(ForYouModule, AuthModule)
    single(named("ioDispatcher")) { Dispatchers.IO }
    single(named("defaultDispatcher")) { Dispatchers.Default }

    viewModelOf(::AppViewModel)
}