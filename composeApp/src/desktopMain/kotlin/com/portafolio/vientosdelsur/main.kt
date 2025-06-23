package com.portafolio.vientosdelsur

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.f776.japanesedictionary.core.resource.Res
import com.f776.japanesedictionary.core.resource.ic_launcher_playstore
import com.portafolio.vientosdelsur.di.initKoin
import org.jetbrains.compose.reload.DevelopmentEntryPoint
import org.jetbrains.compose.resources.painterResource

fun main() {
    initializeFirebase()
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Vientos Del Sur",
            icon = painterResource(Res.drawable.ic_launcher_playstore),
        ) {
            DevelopmentEntryPoint {
                App(
                    darkTheme = isSystemInDarkTheme()
                )
            }
        }
    }
}