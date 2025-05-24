package com.portafolio.vientosdelsur

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.portafolio.vientosdelsur.di.initKoin
import org.jetbrains.compose.reload.DevelopmentEntryPoint

fun main() {
    initializeFirebase()
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "VientosDelSur",
        ) {
            DevelopmentEntryPoint {
                App(
                    darkTheme = isSystemInDarkTheme()
                )
            }
        }
    }
}