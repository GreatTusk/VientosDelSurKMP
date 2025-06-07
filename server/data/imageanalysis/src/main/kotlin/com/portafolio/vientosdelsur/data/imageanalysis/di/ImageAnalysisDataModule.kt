package com.portafolio.vientosdelsur.data.imageanalysis.di

import com.portafolio.vientosdelsur.data.imageanalysis.repository.AzureImageAnalysisRepository
import com.portafolio.vientosdelsur.domain.imageanalysis.ImageAnalysisRepository
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val ImageAnalysisDataModule = module {
    single {
        CustomVisionPredictorFactory.create()
    }

    single {
        AzureImageAnalysisRepository(
            predictor = get(),
            ioDispatcher = get(named("ioDispatcher"))
        )
    }.bind<ImageAnalysisRepository>()
}