package com.portafolio.vientosdelsur.feature.auth.screens.signup.data

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

internal actual suspend fun ImageBitmap.toByteArray(): ByteArray = withContext(Dispatchers.IO) {
    val stream = ByteArrayOutputStream()
    this@toByteArray.asAndroidBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream)
    return@withContext stream.toByteArray()
}