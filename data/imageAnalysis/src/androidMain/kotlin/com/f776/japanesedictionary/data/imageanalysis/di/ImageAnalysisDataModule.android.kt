package com.f776.japanesedictionary.data.imageanalysis.di

import com.f776.japanesedictionary.data.imageanalysis.camera.CameraCaptureController
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal actual val PlatformModule: Module = module {
    single { CameraCaptureController(ioDispatcher = get(named("ioDispatcher"))) }
}