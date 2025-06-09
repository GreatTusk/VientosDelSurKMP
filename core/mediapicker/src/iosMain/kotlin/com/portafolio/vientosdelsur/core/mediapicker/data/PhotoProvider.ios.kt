package com.portafolio.vientosdelsur.core.mediapicker.data

import com.portafolio.vientosdelsur.core.mediapicker.model.Photo
import kotlinx.coroutines.flow.StateFlow

internal actual class PhotoProvider : AutoCloseable {
    actual val photo: StateFlow<Photo>
        get() = TODO("Not yet implemented")

    actual fun updatePhoto(photo: Photo) {
    }

    override fun close() {
        TODO("Not yet implemented")
    }
}