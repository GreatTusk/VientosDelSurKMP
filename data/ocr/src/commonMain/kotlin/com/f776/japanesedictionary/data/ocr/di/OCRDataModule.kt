package com.f776.japanesedictionary.data.ocr.di

import org.koin.core.module.Module
import org.koin.dsl.module

val OCRDataModule = module {
    includes(PlatformModule)
}

internal expect val PlatformModule: Module