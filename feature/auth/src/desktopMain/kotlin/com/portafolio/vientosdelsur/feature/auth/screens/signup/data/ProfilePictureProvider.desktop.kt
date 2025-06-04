package com.portafolio.vientosdelsur.feature.auth.screens.signup.data

import androidx.compose.ui.graphics.decodeToImageBitmap
import com.portafolio.vientosdelsur.feature.auth.screens.signup.ProfilePicture
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

internal actual class ProfilePictureProvider(
    private val coroutineScope: CoroutineScope
) : AutoCloseable {

    private val _profilePicture = MutableStateFlow<ProfilePicture>(ProfilePicture.None)
    actual val profilePicture = _profilePicture.asStateFlow()

    fun onPhotoSelected(file: File) {
        coroutineScope.launch {
            val bitmap = file.readBytes().decodeToImageBitmap()

            _profilePicture.update {
                ProfilePicture.Image(bitmap)
            }
        }
    }

    actual fun updateProfilePicture(profilePicture: ProfilePicture) {
        _profilePicture.update { profilePicture }
    }

    override fun close() = coroutineScope.cancel()
}