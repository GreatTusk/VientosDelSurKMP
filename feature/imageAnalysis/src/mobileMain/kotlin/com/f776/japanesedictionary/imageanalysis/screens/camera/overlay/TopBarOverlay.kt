@file:OptIn(ExperimentalMaterial3Api::class)

package com.f776.japanesedictionary.imageanalysis.screens.camera.overlay

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.f776.core.ui.theme.VientosDelSurTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun TopBarOverlay(modifier: Modifier = Modifier, onNavigateUp: () -> Unit) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text("Análisis de aseo", fontWeight = FontWeight.SemiBold)
        },
        navigationIcon = {
            IconButton(onClick = onNavigateUp) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Exit camera screen",
                )
            }
        }
    )
}

@Preview
@Composable
private fun TopBarOverlayPreview(modifier: Modifier = Modifier) {
    VientosDelSurTheme {
        TopBarOverlay(
            onNavigateUp = {}
        )
    }
}