package com.portafolio.vientosdelsur.core.mediapicker.di

import com.portafolio.vientosdelsur.core.mediapicker.data.PhotoProvider
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal actual val PlatformModule = module {
    single {
        PhotoProvider(get(named("ioCoroutineScope")))
    }
}