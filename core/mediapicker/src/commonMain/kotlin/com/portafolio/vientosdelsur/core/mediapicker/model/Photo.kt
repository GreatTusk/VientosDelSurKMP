package com.portafolio.vientosdelsur.core.mediapicker.model

import androidx.compose.ui.graphics.ImageBitmap

sealed interface Photo {
    data class URL(val url: String) : Photo
    data class Image(val image: ImageBitmap) : Photo
    data object None : Photo
}