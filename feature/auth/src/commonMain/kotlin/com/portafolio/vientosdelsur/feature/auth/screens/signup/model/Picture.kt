package com.portafolio.vientosdelsur.feature.auth.screens.signup.model

import androidx.compose.ui.graphics.ImageBitmap

sealed interface Picture {
    data class URL(val url: String) : Picture
    data class Image(val image: ImageBitmap) : Picture
    data object None : Picture
}