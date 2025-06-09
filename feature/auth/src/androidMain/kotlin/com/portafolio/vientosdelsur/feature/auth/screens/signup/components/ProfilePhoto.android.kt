package com.portafolio.vientosdelsur.feature.auth.screens.signup.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import com.portafolio.vientosdelsur.feature.auth.screens.signup.Picture
import com.portafolio.vientosdelsur.feature.auth.screens.signup.data.ProfilePictureProvider

@Composable
internal actual fun ProfilePhoto(
    picture: Picture,
    profilePictureProvider: ProfilePictureProvider
) {
    val pickMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia(), profilePictureProvider::onPhotoSelected)

    ProfilePhoto(
        picture = picture,
        onClick = { pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }
    )
}