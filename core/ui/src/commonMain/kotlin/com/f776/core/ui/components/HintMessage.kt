package com.f776.core.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.f776.core.ui.theme.VientosDelSurTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HintMessage(modifier: Modifier = Modifier, icon: ImageVector, title: String, subtitle: String) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(56.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyLarge,
            color = LocalContentColor.current.copy(alpha = 0.8f),
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun HintMessagePreview() {
    VientosDelSurTheme {
        Surface {
            HintMessage(
                icon = Icons.Default.SearchOff,
                title = "No results found",
                subtitle = "Please try with a different query."
            )
        }
    }
}