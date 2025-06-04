package com.portafolio.vientosdelsur.feature.auth.screens.signup.data

import android.app.Application
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.ui.graphics.asImageBitmap
import com.portafolio.vientosdelsur.feature.auth.screens.signup.ProfilePicture
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal actual class ProfilePictureProvider(
    private val coroutineScope: CoroutineScope,
    private val application: Application
) : AutoCloseable {

    private val _profilePicture = MutableStateFlow<ProfilePicture>(ProfilePicture.None)
    actual val profilePicture = _profilePicture.asStateFlow()

    fun onPhotoSelected(uri: Uri?) {
        if (uri == null) return

        coroutineScope.launch {
            val inputStream = application.contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream).asImageBitmap()
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