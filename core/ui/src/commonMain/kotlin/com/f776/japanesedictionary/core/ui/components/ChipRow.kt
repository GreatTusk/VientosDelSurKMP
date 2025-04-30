package com.f776.japanesedictionary.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.f776.japanesedictionary.core.ui.theme.JapaneseDictionaryTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChipRow(
    modifier: Modifier = Modifier,
    onChipPressed: () -> Unit,
    contentPaddingValues: PaddingValues = PaddingValues(horizontal = 24.dp),
    items: List<String>,
    textColor: Color = LocalContentColor.current
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = contentPaddingValues
    ) {
        items(items = items) {
            SuggestionChip(
                label = {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.labelMedium,
                        color = textColor
                    )
                },
                onClick = onChipPressed,
                modifier = Modifier.height(24.dp)
            )
        }
    }
}

@Preview
@Composable
private fun ChipRowPreview(modifier: Modifier = Modifier) {
    JapaneseDictionaryTheme {
        Surface {
            ChipRow(
                onChipPressed = {},
                items = listOf("Verb", "Noun", "Adjective", "Particle", "Preposition")
            )
        }
    }
}