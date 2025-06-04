package com.portafolio.vientosdelsur.feature.auth.screens.signup.data

import com.portafolio.vientosdelsur.feature.auth.screens.signup.UserProfilePicture
import kotlinx.coroutines.flow.StateFlow

internal expect class ProfilePictureProvider: AutoCloseable {
    val profilePicture: StateFlow<UserProfilePicture>
    fun updateProfilePicture(profilePicture: UserProfilePicture)
}