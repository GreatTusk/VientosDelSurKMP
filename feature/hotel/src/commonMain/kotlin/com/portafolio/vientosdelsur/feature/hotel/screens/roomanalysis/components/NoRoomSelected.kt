package com.portafolio.vientosdelsur.feature.hotel.screens.roomanalysis.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.f776.core.ui.components.HintMessage
import com.f776.core.ui.theme.VientosDelSurTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun NoRoomSelected(modifier: Modifier = Modifier) = HintMessage(
    modifier = modifier,
    icon = Icons.AutoMirrored.Filled.Help,
    title = "No has seleccionado una foto",
    subtitle = "Selecciona una para inspeccionarla"
)

@Preview
@Composable
private fun NoResultsFoundPreview() {
    VientosDelSurTheme {
        Surface {
            NoRoomSelected()
        }
    }
}