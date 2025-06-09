package com.portafolio.vientosdelsur.core.mediapicker.data

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asSkiaBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

actual suspend fun ImageBitmap.toByteArray(): ByteArray = withContext(Dispatchers.IO) {
    return@withContext asSkiaBitmap().readPixels() ?: ByteArray(size = 0)
}