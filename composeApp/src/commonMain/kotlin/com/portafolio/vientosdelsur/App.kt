package com.portafolio.vientosdelsur

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.f776.core.ui.theme.VientosDelSurTheme
import com.portafolio.vientosdelsur.navigation.RootNavigationGraph

@Composable
fun App(darkTheme: Boolean) {
    VientosDelSurTheme(darkTheme = darkTheme) {
        Surface {
            RootNavigationGraph()
        }
    }
}