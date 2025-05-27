package com.portafolio.vientosdelsur.data.auth.di

import com.portafolio.vientosdelsur.data.auth.network.FirebaseAuthService
import com.portafolio.vientosdelsur.data.auth.network.FirebaseUserRepository
import com.portafolio.vientosdelsur.domain.auth.AuthService
import com.portafolio.vientosdelsur.domain.auth.UserRepository
import com.portafolio.vientosdelsur.domain.auth.signin.SignInUseCase
import com.portafolio.vientosdelsur.domain.auth.signup.SignUpUseCase
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val AuthDataModule = module {
    includes(PlatformModule)
    single { Firebase.auth }
    single { FirebaseAuthService(get(), get(named("ioDispatcher"))) }.bind<AuthService>()
    single {
        FirebaseUserRepository(
            firebaseAuth = get(),
            employeeRepository = get(),
            ioDispatcher = get(named("ioDispatcher"))
        )
    }.bind<UserRepository>()
    factory { SignUpUseCase(get(), get(named("defaultDispatcher"))) }
    factory { SignInUseCase(get(), get(named("defaultDispatcher"))) }
}

internal expect val PlatformModule: Module