package com.portafolio.vientosdelsur.data.auth.di

import androidx.credentials.CredentialManager
import com.portafolio.vientosdelsur.data.auth.GoogleAuthServiceImpl
import com.portafolio.vientosdelsur.data.auth.network.FirebaseUserRepository
import com.portafolio.vientosdelsur.domain.auth.UserRepository
import com.portafolio.vientosdelsur.domain.auth.oauth.GoogleAuthService
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

internal actual val PlatformModule = module {
    single { CredentialManager.create(get()) }
    singleOf(::GoogleAuthServiceImpl).bind<GoogleAuthService>()

    single {
        FirebaseUserRepository(
            firebaseAuth = get(),
            employeeRepository = get(),
            ioDispatcher = get(named("ioDispatcher")),
            coroutineScope = get(named("defaultCoroutineScope"))
        )
    }.bind<UserRepository>()
}