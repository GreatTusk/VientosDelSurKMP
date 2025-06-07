package com.portafolio.vientosdelsur.controller.imageanalysis.di

import com.portafolio.vientosdelsur.service.imageanalysis.di.ImageAnalysisModule
import org.koin.dsl.module

val ImageAnalysisControllerModule = module {
    includes(ImageAnalysisModule)
}