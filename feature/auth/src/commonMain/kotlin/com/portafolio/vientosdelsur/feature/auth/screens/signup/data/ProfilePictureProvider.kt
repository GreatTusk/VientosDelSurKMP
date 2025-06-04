package com.portafolio.vientosdelsur.feature.auth.screens.signup.data

import com.portafolio.vientosdelsur.feature.auth.screens.signup.UserProfilePicture

internal expect class ProfilePictureProvider: AutoCloseable {
    var profilePicture: UserProfilePicture
        private set
}