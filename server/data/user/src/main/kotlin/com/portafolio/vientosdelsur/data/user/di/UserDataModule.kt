package com.portafolio.vientosdelsur.data.user.di

import com.portafolio.vientosdelsur.core.firebase.di.FirebaseModule
import com.portafolio.vientosdelsur.data.user.repository.FirebaseUserRepository
import com.portafolio.vientosdelsur.domain.user.UserRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val UserDataModule = module {
    includes(FirebaseModule)
    singleOf(::FirebaseUserRepository).bind<UserRepository>()
}