package com.portafolio.vientosdelsur.feature.auth.screens.signup.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.decodeToImageBitmap
import com.portafolio.vientosdelsur.feature.auth.screens.signup.UserProfilePicture
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.io.File

internal actual class ProfilePictureProvider(private val coroutineScope: CoroutineScope) : AutoCloseable {
    actual var profilePicture by mutableStateOf<UserProfilePicture>(UserProfilePicture.None)
        private set

    fun onPhotoSelected(file: File) {
        coroutineScope.launch {
            val bitmap = file.readBytes().decodeToImageBitmap()
            profilePicture = UserProfilePicture.Image(bitmap)
        }
    }

    override fun close() {
        coroutineScope.cancel()
    }
}