package com.portafolio.vientosdelsur.data.auth.di

import com.portafolio.vientosdelsur.data.auth.GoogleAuthServiceImpl
import com.portafolio.vientosdelsur.domain.auth.GoogleAuthService
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal actual val PlatformModule: Module = module {
    singleOf(::GoogleAuthServiceImpl).bind<GoogleAuthService>()
}