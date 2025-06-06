package com.f776.japanesedictionary.imageanalysis.screens.camera.overlay

import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import com.f776.japanesedictionary.domain.ocr.OCRResult
import com.f776.japanesedictionary.imageanalysis.screens.camera.mapper.mapToViewCoordinates
import com.f776.japanesedictionary.imageanalysis.screens.camera.mapper.toComposeRect

@Composable
internal fun OCRResultOverlay(
    modifier: Modifier = Modifier,
    detectedText: OCRResult,
    originalImageWidth: Int,
    originalImageHeight: Int
) {
    Spacer(
        modifier = modifier.graphicsLayer {
            compositingStrategy = CompositingStrategy.Offscreen
        }.drawWithCache {
            val canvasWidth = size.width
            val canvasHeight = size.height

            val transparentPath = Path()
            detectedText.textLines.forEach { line ->
                val mappedBox = line.boundingBox.mapToViewCoordinates(
                    sourceWidth = originalImageWidth,
                    sourceHeight = originalImageHeight,
                    viewWidth = canvasWidth,
                    viewHeight = canvasHeight
                )
                transparentPath.addRect(mappedBox.toComposeRect())
            }

            onDrawBehind {
                drawRect(color = Color(0x60000000))
                drawPath(
                    path = transparentPath,
                    color = Color.Black,
                    blendMode = BlendMode.Clear
                )
            }
        }
    )
}