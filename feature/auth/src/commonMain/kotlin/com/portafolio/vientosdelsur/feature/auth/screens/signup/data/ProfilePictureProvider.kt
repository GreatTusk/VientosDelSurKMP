package com.portafolio.vientosdelsur.feature.auth.screens.signup.data

import com.portafolio.vientosdelsur.feature.auth.screens.signup.Picture
import kotlinx.coroutines.flow.StateFlow

internal expect class ProfilePictureProvider: AutoCloseable {
    val picture: StateFlow<Picture>
    fun updateProfilePicture(picture: Picture)
}