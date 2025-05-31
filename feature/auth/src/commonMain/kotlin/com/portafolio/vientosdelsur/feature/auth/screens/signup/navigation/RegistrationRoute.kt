package com.portafolio.vientosdelsur.feature.auth.screens.signup.navigation

import androidx.navigation.NavHostController
import kotlinx.serialization.Serializable

internal sealed interface RegistrationRoute {
    val progress: Float

    @Serializable
    data object Profile : RegistrationRoute {
        override val progress = 0.25f
    }

    @Serializable
    data object Occupation : RegistrationRoute {
        override val progress = 0.5f
    }
}

internal fun NavHostController.navigateToProfile() = navigate(RegistrationRoute.Profile)
internal fun NavHostController.navigateToOccupation() = navigate(RegistrationRoute.Occupation)