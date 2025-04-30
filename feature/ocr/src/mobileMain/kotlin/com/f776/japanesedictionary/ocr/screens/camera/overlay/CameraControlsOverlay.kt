package com.f776.japanesedictionary.ocr.screens.camera.overlay

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.f776.japanesedictionary.core.ui.theme.JapaneseDictionaryTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun CameraControlsOverlay(
    modifier: Modifier = Modifier,
    onOpenGallery: () -> Unit,
    onNavigateToTranslation: () -> Unit,
    onNavigateToDictionary: () -> Unit,
    onImageCaptured: () -> Unit, 
    showCamera: Boolean
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .border(2.dp, color = Color.White, shape = CircleShape)
                .size(60.dp)
                .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            FilledIconButton(
                modifier = Modifier.size(48.dp),
                onClick = onOpenGallery,
                colors = IconButtonDefaults.filledIconButtonColors().copy(
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Image,
                    contentDescription = "Select an image from your gallery"
                )
            }

        }
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .border(2.dp, color = Color.White, shape = CircleShape)
                .size(92.dp)
                .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            FilledIconButton(
                modifier = Modifier.size(76.dp).clip(CircleShape),
                onClick = if (showCamera) onImageCaptured else onNavigateToDictionary,
                colors = IconButtonDefaults.filledIconButtonColors().copy(
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
            ) {
                Icon(
                    modifier = Modifier.size(38.dp),
                    imageVector = if (showCamera) Icons.Default.PhotoCamera else Icons.Default.Search,
                    contentDescription = "Take a picture for analysis"
                )
            }

        }

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .border(2.dp, color = Color.White, shape = CircleShape)
                .size(60.dp)
                .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            FilledIconButton(
                modifier = Modifier.size(48.dp),
                onClick = onNavigateToTranslation,
                colors = IconButtonDefaults.filledIconButtonColors().copy(
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Translate,
                    contentDescription = "Translate text"
                )
            }

        }

    }
}

@Preview
@Composable
private fun CameraControlsOverlayPreview(modifier: Modifier = Modifier) {
    JapaneseDictionaryTheme {
        CameraControlsOverlay(
            onOpenGallery = {},
            onImageCaptured = {},
            onNavigateToTranslation = {},
            onNavigateToDictionary = {},
            showCamera = true
        )
    }
}