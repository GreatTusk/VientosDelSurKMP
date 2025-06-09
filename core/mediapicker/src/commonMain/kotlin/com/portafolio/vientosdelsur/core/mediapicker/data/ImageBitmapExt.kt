package com.portafolio.vientosdelsur.core.mediapicker.data

import androidx.compose.ui.graphics.ImageBitmap

expect suspend fun ImageBitmap.toByteArray(): ByteArray