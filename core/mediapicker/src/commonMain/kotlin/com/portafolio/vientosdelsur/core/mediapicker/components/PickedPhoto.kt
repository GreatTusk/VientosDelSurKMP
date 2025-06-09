package com.portafolio.vientosdelsur.core.mediapicker.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.portafolio.vientosdelsur.core.mediapicker.data.PhotoProvider
import com.portafolio.vientosdelsur.core.mediapicker.model.Photo
import org.koin.compose.koinInject

@Composable
internal fun PickedPhoto(photo: Photo, onClick: () -> Unit) {
    when (photo) {
        is Photo.URL -> {
            PhotoPicker(onClick = onClick) {
                AsyncImage(
                    modifier = Modifier.size(96.dp).clip(CircleShape).align(Alignment.Center),
                    model = photo.url,
                    contentDescription = "Profile picture",
                    contentScale = ContentScale.Crop
                )
            }
        }

        is Photo.Image -> {
            PhotoPicker(onClick = onClick) {
                Image(
                    modifier = Modifier.size(96.dp).clip(CircleShape).align(Alignment.Center),
                    bitmap = photo.image,
                    contentDescription = "Profile picture",
                    contentScale = ContentScale.Crop
                )
            }
        }

        else -> {
            PhotoPicker(onClick = onClick) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    modifier = Modifier.size(96.dp).align(Alignment.Center),
                    contentDescription = "Profile picture"
                )
            }
        }
    }
}

@Composable
expect fun PickedPhoto(
    photo: Photo,
    photoProvider: PhotoProvider = koinInject()
)