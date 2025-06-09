package com.portafolio.vientosdelsur.core.mediapicker.data

import com.portafolio.vientosdelsur.core.mediapicker.model.Photo
import kotlinx.coroutines.flow.StateFlow

internal expect class PhotoProvider: AutoCloseable {
    val photo: StateFlow<Photo>
    fun updatePhoto(photo: Photo)
}