package com.portafolio.vientosdelsur.service.imageanalysis.di

import com.portafolio.vientosdelsur.service.imageanalysis.AzureImageAnalysisService
import com.portafolio.vientosdelsur.service.imageanalysis.ImageAnalysisService
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val ImageAnalysisModule = module {
    single {
        CustomVisionPredictorFactory.create()
    }

    single {
        AzureImageAnalysisService(
            predictor = get(),
            ioDispatcher = get(named("ioDispatcher"))
        )
    }.bind<ImageAnalysisService>()
}