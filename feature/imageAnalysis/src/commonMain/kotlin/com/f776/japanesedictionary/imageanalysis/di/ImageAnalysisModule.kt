package com.f776.japanesedictionary.imageanalysis.di

import com.f776.japanesedictionary.data.imageanalysis.di.ImageAnalysisDataModule
import org.koin.core.module.Module
import org.koin.dsl.module

val ImageAnalysisModule = module { includes(ImageAnalysisDataModule, PlatformModule) }

internal expect val PlatformModule: Module