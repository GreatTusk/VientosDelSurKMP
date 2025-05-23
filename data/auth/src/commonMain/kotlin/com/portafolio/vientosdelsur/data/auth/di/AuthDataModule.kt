package com.portafolio.vientosdelsur.data.auth.di

import com.portafolio.vientosdelsur.data.auth.network.FirebaseAuthService
import com.portafolio.vientosdelsur.data.auth.network.FirebaseUserRepository
import com.portafolio.vientosdelsur.domain.auth.AuthService
import com.portafolio.vientosdelsur.domain.auth.UserRepository
import com.portafolio.vientosdelsur.domain.auth.signup.SignUpUseCase
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val AuthDataModule = module {
    single { Firebase.auth }
    singleOf(::FirebaseAuthService).bind<AuthService>()
    singleOf(::FirebaseUserRepository).bind<UserRepository>()
    factoryOf(::SignUpUseCase)
}