package com.portafolio.vientosdelsur.core.firebase.util

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import java.io.ByteArrayInputStream

internal object FirebaseFactory {
    fun initialize(): FirebaseApp {
        val credentials = System.getenv("FIREBASE_SERVICE_ACCOUNT")?.let {
            GoogleCredentials.fromStream(ByteArrayInputStream(it.toByteArray()))
        } ?: GoogleCredentials.getApplicationDefault()

        val options = FirebaseOptions.builder()
            .setCredentials(credentials)
            .build()

        return FirebaseApp.initializeApp(options)
    }
}