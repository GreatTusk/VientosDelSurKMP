package com.portafolio.vientosdelsur.di

import com.f776.japanesedictionary.imageanalysis.di.ImageAnalysisModule
import com.portafolio.vientosdelsur.AppViewModel
import com.portafolio.vientosdelsur.feature.auth.di.AuthModule
import com.portafolio.vientosdelsur.foryou.di.ForYouModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val AppModule = module {
    includes(ForYouModule, AuthModule, ImageAnalysisModule)
    single(named("ioDispatcher")) { Dispatchers.IO }
    single(named("defaultDispatcher")) { Dispatchers.Default }
    factory(named("ioCoroutineScope")) { CoroutineScope(Dispatchers.IO + SupervisorJob()) }
    factory(named("defaultCoroutineScope")) { CoroutineScope(Dispatchers.Default + SupervisorJob()) }

    viewModelOf(::AppViewModel)
}