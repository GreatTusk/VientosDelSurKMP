package com.portafolio.vientosdelsur.feature.auth.di

import com.portafolio.vientosdelsur.data.auth.di.AuthDataModule
import com.portafolio.vientosdelsur.feature.auth.screens.AuthViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val AuthModule = module {
    includes(AuthDataModule)
    viewModelOf(::AuthViewModel)
}