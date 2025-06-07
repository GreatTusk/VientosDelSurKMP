package com.portafolio.vientosdelsur.service.imageanalysis.di

import com.portafolio.vientosdelsur.data.imageanalysis.di.ImageAnalysisDataModule
import com.portafolio.vientosdelsur.service.imageanalysis.AzureImageAnalysisService
import com.portafolio.vientosdelsur.service.imageanalysis.ImageAnalysisService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val ImageAnalysisModule = module {
    includes(ImageAnalysisDataModule)
    singleOf(::AzureImageAnalysisService).bind<ImageAnalysisService>()
}