package com.portafolio.vientosdelsur.data.auth.di

import com.portafolio.vientosdelsur.data.auth.login.repository.FirebaseSignInRepository
import com.portafolio.vientosdelsur.data.auth.register.repository.FirebaseSignUpRepository
import com.portafolio.vientosdelsur.domain.auth.login.SignInRepository
import com.portafolio.vientosdelsur.domain.auth.register.SignUpRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val AuthDataModule = module {
    single { Firebase.auth }
    factoryOf(::FirebaseSignInRepository).bind<SignInRepository>()
    factoryOf(::FirebaseSignUpRepository).bind<SignUpRepository>()
}