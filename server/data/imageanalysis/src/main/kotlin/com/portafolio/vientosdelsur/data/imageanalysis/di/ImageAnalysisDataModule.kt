package com.portafolio.vientosdelsur.data.imageanalysis.di

import com.f776.core.network.di.NetworkModule
import com.portafolio.vientosdelsur.data.imageanalysis.repository.AzureImageAnalysisRepository
import com.portafolio.vientosdelsur.data.imageanalysis.repository.DbImageStorageRepository
import com.portafolio.vientosdelsur.domain.imageanalysis.ImageAnalysisRepository
import com.portafolio.vientosdelsur.domain.imageanalysis.storage.ImageStorageRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val ImageAnalysisDataModule = module {
    includes(NetworkModule)
    singleOf(::AzureImageAnalysisRepository).bind<ImageAnalysisRepository>()
    single { DbImageStorageRepository }.bind<ImageStorageRepository>()
}