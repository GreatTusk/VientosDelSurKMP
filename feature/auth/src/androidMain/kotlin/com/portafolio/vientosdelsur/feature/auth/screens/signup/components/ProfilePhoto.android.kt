package com.portafolio.vientosdelsur.feature.auth.screens.signup.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import com.portafolio.vientosdelsur.feature.auth.screens.signup.UserProfilePicture
import com.portafolio.vientosdelsur.feature.auth.screens.signup.data.ProfilePictureProvider
import org.koin.compose.koinInject

@Composable
internal actual fun ProfilePhoto(
    userProfilePicture: UserProfilePicture,
    profilePictureProvider: ProfilePictureProvider
) {
    val pickMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia(), profilePictureProvider::onPhotoSelected)

    ProfilePhoto(
        userProfilePicture = userProfilePicture,
        onClick = { pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }
    )
}