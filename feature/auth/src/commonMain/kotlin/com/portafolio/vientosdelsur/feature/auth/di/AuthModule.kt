package com.portafolio.vientosdelsur.feature.auth.di

import com.portafolio.vientosdelsur.data.auth.di.AuthDataModule
import org.koin.dsl.module

val AuthModule = module {
    includes(AuthDataModule)
}