package com.f776.japanesedictionary.data.imageanalysis.di

import com.f776.core.network.di.NetworkModule
import com.f776.japanesedictionary.data.imageanalysis.repository.KtorImageAnalysisService
import com.f776.japanesedictionary.domain.imageanalysis.ImageAnalysisService
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val OCRDataModule = module {
    includes(PlatformModule, NetworkModule)
    singleOf(::KtorImageAnalysisService).bind<ImageAnalysisService>()
}

internal expect val PlatformModule: Module