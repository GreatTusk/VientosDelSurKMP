package com.portafolio.vientosdelsur.feature.auth.screens.signup.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import com.portafolio.vientosdelsur.feature.auth.screens.signup.ProfilePicture
import com.portafolio.vientosdelsur.feature.auth.screens.signup.data.ProfilePictureProvider

@Composable
internal actual fun ProfilePhoto(
    profilePicture: ProfilePicture,
    profilePictureProvider: ProfilePictureProvider
) {
    val pickMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia(), profilePictureProvider::onPhotoSelected)

    ProfilePhoto(
        profilePicture = profilePicture,
        onClick = { pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }
    )
}