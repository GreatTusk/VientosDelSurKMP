package com.f776.japanesedictionary.imageanalysis.di

import com.f776.japanesedictionary.data.ocr.di.OCRDataModule
import org.koin.core.module.Module
import org.koin.dsl.module

val ImageAnalysisModule = module { includes(OCRDataModule, PlatformModule) }

internal expect val PlatformModule: Module