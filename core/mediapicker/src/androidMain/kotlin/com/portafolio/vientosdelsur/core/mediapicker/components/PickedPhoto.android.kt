package com.portafolio.vientosdelsur.core.mediapicker.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import com.portafolio.vientosdelsur.core.mediapicker.data.PhotoProvider
import com.portafolio.vientosdelsur.core.mediapicker.model.Photo

@Composable
internal actual fun PickedPhoto(
    photo: Photo,
    photoProvider: PhotoProvider
) {
    val pickMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia(), photoProvider::onPhotoSelected)

    PickedPhoto(
        photo = photo,
        onClick = { pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }
    )
}