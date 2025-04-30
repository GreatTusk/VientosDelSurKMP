package com.f776.japanesedictionary.ocr.screens.camera.overlay

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.f776.japanesedictionary.core.ui.theme.JapaneseDictionaryTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun FocusBoxOverlay(modifier: Modifier = Modifier) {
    Spacer(
        modifier = modifier.drawWithCache {
            val width = size.width
            val height = size.height
            val strokeWidth = 4f
            val strokeOffset = strokeWidth / 2

            val cornerPath = Path().apply {
                moveTo(strokeOffset, height / 4)
                quadraticTo(
                    x2 = width / 4, y2 = strokeOffset,
                    x1 = 0f, y1 = 0f
                )

                moveTo(width - strokeOffset, height / 4)
                quadraticTo(
                    x2 = width - width / 4, y2 = strokeOffset,
                    x1 = width, y1 = 0f
                )

                moveTo(x = width - strokeOffset, y = height - height / 4)
                quadraticTo(
                    x2 = width - width / 4, y2 = height - strokeOffset,
                    x1 = width, y1 = height
                )

                moveTo(x = width / 4, y = height - strokeOffset)
                quadraticTo(
                    x2 = strokeOffset, y2 = height - height / 4,
                    x1 = 0f, y1 = height
                )
            }

            onDrawBehind {
                drawPath(
                    color = Color.White,
                    path = cornerPath,
                    style = Stroke(width = 4f)
                )
            }
        }
    )
}

@Preview
@Composable
private fun CornerFrameOverlayPreview(modifier: Modifier = Modifier) {
    JapaneseDictionaryTheme {
        FocusBoxOverlay(modifier = Modifier.size(64.dp))
    }
}