package com.portafolio.vientosdelsur.feature.auth.screens.signup.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.f776.core.ui.theme.VientosDelSurTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun ProfilePhotoPicker(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    profilePhoto: @Composable BoxScope.() -> Unit
) {
    Box(modifier = Modifier.size(128.dp)) {
        profilePhoto()
        FilledIconButton(
            onClick = onClick,
            modifier = Modifier.align(Alignment.BottomEnd).padding(bottom = 8.dp, end = 8.dp)
        ) {
            Icon(imageVector = Icons.Default.AddPhotoAlternate, contentDescription = "Select another image")
        }
    }
}

@Preview
@Composable
private fun PhotoPickerPreview() {
    VientosDelSurTheme {
        Surface {
            ProfilePhotoPicker(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    modifier = Modifier.size(96.dp).clip(CircleShape).align(Alignment.Center),
                    contentDescription = "Profile picture"
                )
            }
        }
    }
}