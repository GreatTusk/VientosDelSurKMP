package com.portafolio.vientosdelsur.feature.auth.screens.signup.data

import com.portafolio.vientosdelsur.feature.auth.screens.signup.ProfilePicture
import kotlinx.coroutines.flow.StateFlow

internal expect class ProfilePictureProvider: AutoCloseable {
    val profilePicture: StateFlow<ProfilePicture>
    fun updateProfilePicture(profilePicture: ProfilePicture)
}