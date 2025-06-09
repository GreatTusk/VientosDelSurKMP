package com.portafolio.vientosdelsur.core.mediapicker.di

import org.koin.core.module.Module
import org.koin.dsl.module

val PhotoPickerModule = module {
    includes(PlatformModule)
}

internal expect val PlatformModule: Module