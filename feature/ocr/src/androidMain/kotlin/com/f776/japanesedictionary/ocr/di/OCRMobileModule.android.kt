package com.f776.japanesedictionary.ocr.di

import com.f776.japanesedictionary.ocr.screens.camera.OCRViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

internal actual val PlatformModule: Module = module {
    viewModelOf(::OCRViewModel)
}