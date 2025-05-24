package com.portafolio.vientosdelsur.data.auth.di

import androidx.credentials.CredentialManager
import com.portafolio.vientosdelsur.data.auth.GoogleAuthServiceImpl
import com.portafolio.vientosdelsur.domain.auth.GoogleAuthService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal actual val PlatformModule = module {
    single { CredentialManager.create(get()) }
    singleOf(::GoogleAuthServiceImpl).bind<GoogleAuthService>()
}