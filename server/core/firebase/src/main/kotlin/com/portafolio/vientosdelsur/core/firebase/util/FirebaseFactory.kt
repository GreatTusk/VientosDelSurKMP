package com.portafolio.vientosdelsur.core.firebase.util

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions

internal object FirebaseFactory {
    private val options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.getApplicationDefault())
        .build()

    fun initialize(): FirebaseApp = FirebaseApp.initializeApp(options)
}