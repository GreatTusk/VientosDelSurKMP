package com.portafolio.vientosdelsur.di

import com.portafolio.vientosdelsur.di.AppModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

internal fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(AppModule)
    }
}