package com.portafolio.vientosdelsur.feature.auth.screens.signup.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.f776.core.ui.theme.VientosDelSurTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun PhotoPicker(modifier: Modifier = Modifier, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            modifier = Modifier.size(64.dp),
            imageVector = Icons.Default.AddAPhoto,
            contentDescription = "Imagen de perfil",
        )
    }
}

@Preview
@Composable
private fun PhotoPickerPreview() {
    VientosDelSurTheme {
        Surface {
            PhotoPicker(onClick = {})
        }
    }
}