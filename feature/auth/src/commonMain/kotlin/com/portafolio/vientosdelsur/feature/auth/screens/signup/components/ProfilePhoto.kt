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
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.portafolio.vientosdelsur.feature.auth.screens.signup.UserProfilePicture
import com.portafolio.vientosdelsur.feature.auth.screens.signup.data.ProfilePictureProvider
import org.koin.compose.koinInject

@Composable
internal fun ProfilePhoto(userProfilePicture: UserProfilePicture, onClick: () -> Unit) {
    when (userProfilePicture) {
        is UserProfilePicture.URL -> {
            ProfilePhotoPicker(onClick = onClick) {
                AsyncImage(
                    modifier = Modifier.size(96.dp).clip(CircleShape).align(Alignment.Center),
                    model = userProfilePicture.url,
                    contentDescription = "Profile picture"
                )
            }
        }

        is UserProfilePicture.Image -> {
            ProfilePhotoPicker(onClick = onClick) {
                Image(
                    modifier = Modifier.size(96.dp).clip(CircleShape),
                    bitmap = userProfilePicture.image,
                    contentDescription = "Profile picture"
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
    userProfilePicture: UserProfilePicture,
    profilePictureProvider: ProfilePictureProvider = koinInject()
)