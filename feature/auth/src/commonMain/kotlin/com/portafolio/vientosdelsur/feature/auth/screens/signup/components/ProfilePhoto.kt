package com.portafolio.vientosdelsur.feature.auth.screens.signup.components

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
import com.portafolio.vientosdelsur.feature.auth.screens.signup.Picture
import com.portafolio.vientosdelsur.feature.auth.screens.signup.data.ProfilePictureProvider
import org.koin.compose.koinInject

@Composable
internal fun ProfilePhoto(picture: Picture, onClick: () -> Unit) {
    when (picture) {
        is Picture.URL -> {
            ProfilePhotoPicker(onClick = onClick) {
                AsyncImage(
                    modifier = Modifier.size(96.dp).clip(CircleShape).align(Alignment.Center),
                    model = picture.url,
                    contentDescription = "Profile picture",
                    contentScale = ContentScale.Crop
                )
            }
        }

        is Picture.Image -> {
            ProfilePhotoPicker(onClick = onClick) {
                Image(
                    modifier = Modifier.size(96.dp).clip(CircleShape).align(Alignment.Center),
                    bitmap = picture.image,
                    contentDescription = "Profile picture",
                    contentScale = ContentScale.Crop
                )
            }
        }

        else -> {
            ProfilePhotoPicker(onClick = onClick) {
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
internal expect fun ProfilePhoto(
    picture: Picture,
    profilePictureProvider: ProfilePictureProvider = koinInject()
)