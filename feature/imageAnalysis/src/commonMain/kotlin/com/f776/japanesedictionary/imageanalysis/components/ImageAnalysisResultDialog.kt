package com.f776.japanesedictionary.imageanalysis.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import com.f776.core.ui.theme.VientosDelSurTheme
import com.f776.japanesedictionary.domain.imageanalysis.ImageAnalysisResult
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ImageAnalysisResultDialog(
    result: ImageAnalysisResult,
    onDismissRequest: () -> Unit
) {
    val icon: ImageVector
    val message: String
    val title: String

    when (result) {
        ImageAnalysisResult.CLEAN -> {
            icon = Icons.Filled.CheckCircle
            title = "Impecable"
            message = "La habitación está limpia."
        }

        ImageAnalysisResult.SLIGHTLY_DIRTY -> {
            icon = Icons.Filled.Info
            title = "Aceptable"
            message = "La habitación presenta leves detalles, pero está aceptable."
        }

        ImageAnalysisResult.MODERATELY_DIRTY -> {
            icon = Icons.Filled.Warning
            title = "Debe Revisarse"
            message = "La habitación tiene señales visibles de suciedad y debe ser revisada."
        }

        ImageAnalysisResult.VERY_DIRTY -> {
            icon = Icons.Filled.Error
            title = "Suciedad Notoria"
            message = "La habitación está visiblemente sucia y requiere limpieza."
        }

        ImageAnalysisResult.EXTREMELY_DIRTY -> {
            icon = Icons.Filled.Error
            title = "Condiciones Inaceptables"
            message = "La habitación está en condiciones inaceptables y requiere una limpieza profunda urgente."
        }
    }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        icon = { Icon(icon, contentDescription = null) },
        title = { Text(text = title, textAlign = TextAlign.Center) },
        text = { Text(text = message) },
        confirmButton = {
            TextButton(onClick = onDismissRequest) {
                Text("OK")
            }
        }
    )
}

@Preview
@Composable
private fun PreviewCleanResultDialog() {
    VientosDelSurTheme {
        Surface {
            ImageAnalysisResultDialog(result = ImageAnalysisResult.CLEAN, onDismissRequest = {})
        }
    }
}

@Preview
@Composable
private fun PreviewSlightlyDirtyResultDialog() {
    VientosDelSurTheme {
        Surface {
            ImageAnalysisResultDialog(result = ImageAnalysisResult.SLIGHTLY_DIRTY, onDismissRequest = {})
        }
    }
}

@Preview
@Composable
private fun PreviewModeratelyDirtyResultDialog() {
    VientosDelSurTheme {
        Surface {
            ImageAnalysisResultDialog(result = ImageAnalysisResult.MODERATELY_DIRTY, onDismissRequest = {})
        }
    }
}

@Preview
@Composable
private fun PreviewVeryDirtyResultDialog() {
    VientosDelSurTheme {
        Surface {
            ImageAnalysisResultDialog(result = ImageAnalysisResult.VERY_DIRTY, onDismissRequest = {})
        }
    }
}

@Preview
@Composable
private fun PreviewExtremelyDirtyResultDialog() {
    VientosDelSurTheme {
        Surface {
            ImageAnalysisResultDialog(result = ImageAnalysisResult.EXTREMELY_DIRTY, onDismissRequest = {})
        }
    }
}