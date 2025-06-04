package com.portafolio.vientosdelsur.feature.auth.screens.signup.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import com.portafolio.vientosdelsur.feature.auth.screens.signup.UserProfilePicture

@Composable
internal actual fun ProfilePhoto(userProfilePicture: UserProfilePicture) {
    val pickMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->

        }

    ProfilePhoto(
        userProfilePicture = userProfilePicture,
        onClick = { pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }
    )
}