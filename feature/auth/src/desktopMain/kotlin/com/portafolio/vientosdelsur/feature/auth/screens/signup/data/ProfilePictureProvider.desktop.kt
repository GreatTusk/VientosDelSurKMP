package com.portafolio.vientosdelsur.feature.auth.screens.signup.data

import androidx.compose.ui.graphics.decodeToImageBitmap
import com.portafolio.vientosdelsur.feature.auth.screens.signup.Picture
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

internal actual class ProfilePictureProvider(
    private val coroutineScope: CoroutineScope
) : AutoCloseable {

    private val _Picture = MutableStateFlow<Picture>(Picture.None)
    actual val picture = _Picture.asStateFlow()

    fun onPhotoSelected(file: File) {
        coroutineScope.launch {
            val bitmap = file.readBytes().decodeToImageBitmap()

            _Picture.update {
                Picture.Image(bitmap)
            }
        }
    }

    actual fun updateProfilePicture(picture: Picture) {
        _Picture.update { picture }
    }

    override fun close() = coroutineScope.cancel()
}