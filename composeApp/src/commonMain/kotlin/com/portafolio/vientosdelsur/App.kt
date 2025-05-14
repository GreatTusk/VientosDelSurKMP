package com.portafolio.vientosdelsur

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.f776.core.ui.theme.VientosDelSurTheme
import com.portafolio.vientosdelsur.navigation.TopLevelNavigation
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(darkTheme: Boolean) {
    VientosDelSurTheme(darkTheme = darkTheme) {
        Surface {
            TopLevelNavigation()
        }
    }
}