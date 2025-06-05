package com.portafolio.vientosdelsur.core.firebase.di

import org.koin.dsl.module
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth

val FirebaseModule = module {
    single {
        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.getApplicationDefault())
            .build()

        FirebaseApp.initializeApp(options)
    }

    single { FirebaseAuth.getInstance() }
}