@file:OptIn(ExperimentalMaterial3Api::class)

package com.f776.japanesedictionary.ocr.screens.camera.overlay

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
import com.f776.japanesedictionary.core.ui.theme.JapaneseDictionaryTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun TopBarOverlay(modifier: Modifier = Modifier, onNavigateUp: () -> Unit) {
    CenterAlignedTopAppBar(
        modifier = modifier.drawBehind {
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0x40000000),
                        Color(0x35000000),
                        Color(0x30000000),
                        Color(0x25000000),
                        Color(0x20000000),
                        Color(0x15000000),
                        Color(0x10000000),
                        Color(0x05000000),
                        Color.Transparent
                    ),
                    startY = size.height,
                    endY = size.height + 16.dp.toPx()
                ),
                topLeft = Offset(0f, size.height),
                size = Size(width = size.width, height = 16.dp.toPx())
            )
        },
        title = {
            Text("Character recognition", color = Color.White, fontWeight = FontWeight.SemiBold)
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors().copy(
            containerColor = Color(0x40000000)
        ),
        navigationIcon = {
            IconButton(onClick = onNavigateUp) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Exit camera screen",
                    tint = Color.White
                )
            }
        }
    )
}

@Preview
@Composable
private fun TopBarOverlayPreview(modifier: Modifier = Modifier) {
    JapaneseDictionaryTheme {
        TopBarOverlay(
            onNavigateUp = {}
        )
    }
}