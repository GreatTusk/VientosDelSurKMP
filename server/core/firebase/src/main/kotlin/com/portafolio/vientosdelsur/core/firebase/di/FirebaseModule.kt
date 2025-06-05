package com.portafolio.vientosdelsur.core.firebase.di

import com.google.firebase.auth.FirebaseAuth
import com.portafolio.vientosdelsur.core.firebase.util.FirebaseFactory
import org.koin.dsl.module

val FirebaseModule = module {
    single { FirebaseFactory.initialize() }
    single { FirebaseAuth.getInstance() }
}