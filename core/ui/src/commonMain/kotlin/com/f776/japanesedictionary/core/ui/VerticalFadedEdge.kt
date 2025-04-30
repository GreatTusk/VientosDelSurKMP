package com.f776.japanesedictionary.core.ui

import androidx.annotation.FloatRange
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.f776.japanesedictionary.core.ui.components.ChipRow
import com.f776.japanesedictionary.core.ui.components.HorizontalFadedEdge
import com.f776.japanesedictionary.core.ui.theme.JapaneseDictionaryTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun VerticalFadedEdge(
    modifier: Modifier = Modifier,
    @FloatRange(from = 0.0, to = 1.0) startFrom: Float,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
            .drawWithContent {
                drawContent()
                drawRect(
                    brush = Brush.verticalGradient(
                        listOf(Color.Black, Color.Transparent),
                        startY = 0f,
                        endY = startFrom * size.height
                    ),
                    blendMode = BlendMode.DstIn
                )
            }
    ) {
        content()
    }
}

@Preview
@Composable
private fun VerticalFadedEdgePreview() {
    JapaneseDictionaryTheme {
        Surface {
            VerticalFadedEdge(
                startFrom = 1f
            ) {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(listOf("Verb", "Noun", "Adjective", "Particle", "Preposition")) {
                        SuggestionChip(
                            onClick = {},
                            label = { Text(it) },
                        )
                    }
                }
            }
        }
    }
}