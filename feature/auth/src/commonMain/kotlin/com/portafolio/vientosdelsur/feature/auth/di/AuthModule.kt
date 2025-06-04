package com.portafolio.vientosdelsur.feature.auth.di

import com.portafolio.vientosdelsur.data.auth.di.AuthDataModule
import com.portafolio.vientosdelsur.feature.auth.screens.signin.AuthViewModel
import com.portafolio.vientosdelsur.feature.auth.screens.signup.RegistrationFlowViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val AuthModule = module {
    includes(AuthDataModule, PlatformModule)
    single(named("ioCoroutineScope")) { CoroutineScope(Dispatchers.IO + SupervisorJob()) }
    viewModelOf(::AuthViewModel)
    viewModelOf(::RegistrationFlowViewModel)
}

internal expect val PlatformModule: Module