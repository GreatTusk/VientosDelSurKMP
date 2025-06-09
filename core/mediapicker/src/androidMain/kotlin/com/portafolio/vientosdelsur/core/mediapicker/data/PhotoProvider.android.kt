package com.portafolio.vientosdelsur.core.mediapicker.data

import android.app.Application
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.ui.graphics.asImageBitmap
import com.portafolio.vientosdelsur.core.mediapicker.model.Photo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal actual class PhotoProvider(
    private val coroutineScope: CoroutineScope,
    private val application: Application
) : AutoCloseable {

    private val _photo = MutableStateFlow<Photo>(Photo.None)
    actual val photo = _photo.asStateFlow()

    fun onPhotoSelected(uri: Uri?) {
        if (uri == null) return

        coroutineScope.launch {
            val inputStream = application.contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream).asImageBitmap()
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