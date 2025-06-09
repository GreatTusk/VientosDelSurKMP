package com.portafolio.vientosdelsur.core.mediapicker.data

import androidx.compose.ui.graphics.decodeToImageBitmap
import com.portafolio.vientosdelsur.core.mediapicker.model.Photo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

actual class PhotoProvider(
    private val coroutineScope: CoroutineScope
) : AutoCloseable {

    private val _photo = MutableStateFlow<Photo>(Photo.None)
    actual val photo = _photo.asStateFlow()

    fun onPhotoSelected(file: File) {
        coroutineScope.launch {
            val bitmap = file.readBytes().decodeToImageBitmap()

            _photo.update {
                Photo.Image(bitmap)
            }
        }
    }

    actual fun updatePhoto(photo: Photo) {
        _photo.update { photo }
    }

    override fun close() = coroutineScope.cancel()
}