package com.portafolio.vientosdelsur

import android.app.Application
import com.google.firebase.FirebasePlatform
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseOptions
import dev.gitlive.firebase.initialize


fun initializeFirebase() {
    FirebasePlatform.initializeFirebasePlatform(
        object : FirebasePlatform() {
            val storage = mutableMapOf<String, String>()
            override fun store(key: String, value: String) = storage.set(key, value)
            override fun retrieve(key: String) = storage[key]
            override fun clear(key: String) {
                storage.remove(key)
            }
            override fun log(msg: String) = println(msg)
        },
    )

    val options = FirebaseOptions(
        applicationId = "1:447087432043:android:9c4f675660653f9dde3c2e",
        apiKey = "AIzaSyCp7Had4LRX8rGGTKtGQUvqW2veSAPHXNE",
        projectId = "vientosdelsur-81e52",
    )

    Firebase.initialize(Application(), options)
}