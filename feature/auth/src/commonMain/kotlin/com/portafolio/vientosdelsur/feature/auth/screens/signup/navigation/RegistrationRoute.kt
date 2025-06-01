package com.portafolio.vientosdelsur.feature.auth.screens.signup.navigation

import androidx.navigation.NavHostController
import kotlinx.serialization.Serializable

internal sealed interface RegistrationRoute {
    val progress: Float

    @Serializable
    data object Profile : RegistrationRoute {
        override val progress = 0.2f
    }

    @Serializable
    data object Occupation : RegistrationRoute {
        override val progress = 0.4f
    }

    @Serializable
    data object HireDate: RegistrationRoute {
        override val progress = 0.6f
    }

    @Serializable
    data object DayOff: RegistrationRoute {
        override val progress = 0.8f
    }
}

internal fun NavHostController.navigateToProfile() = navigate(RegistrationRoute.Profile)
internal fun NavHostController.navigateToOccupation() = navigate(RegistrationRoute.Occupation)
internal fun NavHostController.navigateToHireDate() = navigate(RegistrationRoute.HireDate)
internal fun NavHostController.navigateToDayOff() = navigate(RegistrationRoute.DayOff)