package com.portafolio.vientosdelsur.feature.auth.screens.signup.data

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asSkiaBitmap

internal actual fun ImageBitmap.toByteArray(): ByteArray {
    return asSkiaBitmap().readPixels() ?: ByteArray(size = 0)
}