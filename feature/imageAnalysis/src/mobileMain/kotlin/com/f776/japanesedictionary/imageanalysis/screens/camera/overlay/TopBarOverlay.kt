@file:OptIn(ExperimentalMaterial3Api::class)

package com.f776.japanesedictionary.imageanalysis.screens.camera.overlay

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KingBed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.f776.core.ui.theme.VientosDelSurTheme
import com.f776.japanesedictionary.imageanalysis.model.RoomSelectionUi
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun TopBarOverlay(
    modifier: Modifier = Modifier,
    selectedRoomSelectionUi: RoomSelectionUi?,
    onNavigateUp: () -> Unit
) {
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
        },
        actions = {
            AnimatedContent(selectedRoomSelectionUi) {
                it?.let {
                    Row(
                        modifier = Modifier.padding(end = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(imageVector = Icons.Default.KingBed, contentDescription = "Selected room")
                        Text(it.number)
                    }
                }
            }
        }
    )
}

@Preview
@Composable
private fun TopBarOverlayPreview(modifier: Modifier = Modifier) {
    VientosDelSurTheme {
        TopBarOverlay(
            onNavigateUp = {},
            selectedRoomSelectionUi = null
        )
    }
}

@Preview
@Composable
private fun TopBarOverlayWithRoomPreview(modifier: Modifier = Modifier) {
    VientosDelSurTheme {
        TopBarOverlay(
            onNavigateUp = {},
            selectedRoomSelectionUi = RoomSelectionUi(1, "102")
        )
    }
}