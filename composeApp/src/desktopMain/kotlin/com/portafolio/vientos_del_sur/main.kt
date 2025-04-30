package com.portafolio.vientos_del_sur

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "VientosDelSur",
    ) {
        App()
    }
}