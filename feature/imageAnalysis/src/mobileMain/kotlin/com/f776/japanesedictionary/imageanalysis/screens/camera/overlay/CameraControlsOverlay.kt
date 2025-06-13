package com.f776.japanesedictionary.imageanalysis.screens.camera.overlay

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.f776.core.ui.theme.VientosDelSurTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun CameraControlsOverlay(
    modifier: Modifier = Modifier,
    onOpenGallery: () -> Unit,
    onImageCaptured: () -> Unit,
    onRoomSelection: () -> Unit,
    isRoomSelected: Boolean
) {
    val galleryBoxSize by animateDpAsState(
        targetValue = if (isRoomSelected) 92.dp else 60.dp,
        animationSpec = tween(durationMillis = 300),
        label = "galleryBoxSize"
    )

    val galleryButtonSize by animateDpAsState(
        targetValue = if (isRoomSelected) 76.dp else 48.dp,
        animationSpec = tween(durationMillis = 300),
        label = "galleryButtonSize"
    )

    val galleryIconSize by animateDpAsState(
        targetValue = if (isRoomSelected) 38.dp else 24.dp,
        animationSpec = tween(durationMillis = 300),
        label = "galleryIconSize"
    )

    val cameraBoxSize by animateDpAsState(
        targetValue = if (isRoomSelected) 92.dp else 60.dp,
        animationSpec = tween(durationMillis = 300),
        label = "cameraBoxSize"
    )

    val cameraButtonSize by animateDpAsState(
        targetValue = if (isRoomSelected) 76.dp else 48.dp,
        animationSpec = tween(durationMillis = 300),
        label = "cameraButtonSize"
    )

    val cameraIconSize by animateDpAsState(
        targetValue = if (isRoomSelected) 38.dp else 24.dp,
        animationSpec = tween(durationMillis = 300),
        label = "cameraIconSize"
    )

    val roomBoxSize by animateDpAsState(
        targetValue = if (isRoomSelected) 60.dp else 92.dp,
        animationSpec = tween(durationMillis = 300),
        label = "roomBoxSize"
    )

    val roomButtonSize by animateDpAsState(
        targetValue = if (isRoomSelected) 48.dp else 76.dp,
        animationSpec = tween(durationMillis = 300),
        label = "roomButtonSize"
    )

    val roomIconSize by animateDpAsState(
        targetValue = if (isRoomSelected) 24.dp else 38.dp,
        animationSpec = tween(durationMillis = 300),
        label = "roomIconSize"
    )

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .border(2.dp, color = Color.White, shape = CircleShape)
                .size(galleryBoxSize)
                .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            FilledIconButton(
                modifier = Modifier.size(galleryButtonSize),
                onClick = onOpenGallery,
                enabled = isRoomSelected,
                colors = IconButtonDefaults.filledIconButtonColors().copy(
                    containerColor = if (isRoomSelected) Color.White else Color.Gray,
                    contentColor = if (isRoomSelected) Color.Black else Color.DarkGray,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.DarkGray
                )
            ) {
                Icon(
                    modifier = Modifier.size(galleryIconSize),
                    imageVector = Icons.Default.Image,
                    contentDescription = "Select an image from your gallery"
                )
            }
        }

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .border(2.dp, color = Color.White, shape = CircleShape)
                .size(cameraBoxSize)
                .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            FilledIconButton(
                modifier = Modifier.size(cameraButtonSize).clip(CircleShape),
                onClick = onImageCaptured,
                enabled = isRoomSelected,
                colors = IconButtonDefaults.filledIconButtonColors().copy(
                    containerColor = if (isRoomSelected) Color.White else Color.Gray,
                    contentColor = if (isRoomSelected) Color.Black else Color.DarkGray,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.DarkGray
                )
            ) {
                Icon(
                    modifier = Modifier.size(cameraIconSize),
                    imageVector = Icons.Default.PhotoCamera,
                    contentDescription = "Take a picture for analysis"
                )
            }
        }

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .border(2.dp, color = Color.White, shape = CircleShape)
                .size(roomBoxSize)
                .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            FilledIconButton(
                modifier = Modifier.size(roomButtonSize),
                onClick = onRoomSelection,
                colors = IconButtonDefaults.filledIconButtonColors().copy(
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
            ) {
                Icon(
                    modifier = Modifier.size(roomIconSize),
                    imageVector = Icons.Default.Bed,
                    contentDescription = "Select a room"
                )
            }
        }
    }
}

@Preview
@Composable
private fun CameraControlsOverlayRoomSelectedPreview(modifier: Modifier = Modifier) {
    VientosDelSurTheme {
        CameraControlsOverlay(
            onOpenGallery = {},
            onImageCaptured = {},
            onRoomSelection = {},
            isRoomSelected = true
        )
    }
}

@Preview
@Composable
private fun CameraControlsOverlayPreview(modifier: Modifier = Modifier) {
    VientosDelSurTheme {
        CameraControlsOverlay(
            onOpenGallery = {},
            onImageCaptured = {},
            onRoomSelection = {},
            isRoomSelected = false
        )
    }
}