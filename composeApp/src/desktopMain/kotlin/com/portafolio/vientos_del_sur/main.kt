package com.portafolio.vientos_del_sur

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.jetbrains.compose.reload.DevelopmentEntryPoint

fun main() = application {
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