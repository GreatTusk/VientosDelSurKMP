package com.f776.japanesedictionary.imageanalysis.di

import com.f776.japanesedictionary.imageanalysis.screens.camera.ImageAnalysisViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

internal actual val PlatformModule: Module = module {
    viewModelOf(::ImageAnalysisViewModel)
}