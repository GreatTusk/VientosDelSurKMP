package com.portafolio.vientosdelsur.feature.auth.screens.signup.data

import android.app.Application
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.asImageBitmap
import com.portafolio.vientosdelsur.feature.auth.screens.signup.UserProfilePicture
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

internal actual class ProfilePictureProvider(
    private val coroutineScope: CoroutineScope,
    private val application: Application
) : AutoCloseable {
    actual var profilePicture by mutableStateOf<UserProfilePicture>(UserProfilePicture.None)
        private set

    fun onPhotoSelected(uri: Uri?) {
        if (uri == null) return

        coroutineScope.launch {
            val inputStream = application.contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream).asImageBitmap()
            profilePicture = UserProfilePicture.Image(bitmap)
        }
    }

    override fun close() {
        coroutineScope.cancel()
    }
}