package com.portafolio.vientosdelsur.config

import com.portafolio.vientosdelsur.di.AppModule
import org.koin.core.context.startKoin

fun configureKoin() {
    startKoin {
        modules(AppModule)
    }
}