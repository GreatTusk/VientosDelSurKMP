package com.portafolio.vientosdelsur.feature.auth.screens.signup.navigation

import androidx.navigation.NavHostController
import kotlinx.serialization.Serializable

internal sealed interface RegistrationRoute {
    val progress: Float

    @Serializable
    data object Profile : RegistrationRoute {
        override val progress = 1 / 6f
    }

    @Serializable
    data object Occupation : RegistrationRoute {
        override val progress = 2 / 6f
    }

    @Serializable
    data object HousekeeperRole : RegistrationRoute {
        override val progress = 3 / 6f
    }

    @Serializable
    data object HireDate : RegistrationRoute {
        override val progress = 4 / 6f
    }

    @Serializable
    data object DayOff : RegistrationRoute {
        override val progress = 5 / 6f
    }
}

internal fun NavHostController.navigateToProfile() = navigate(RegistrationRoute.Profile)
internal fun NavHostController.navigateToOccupation() = navigate(RegistrationRoute.Occupation)
internal fun NavHostController.navigateToHousekeeperRole() = navigate(RegistrationRoute.HousekeeperRole)
internal fun NavHostController.navigateToHireDate() = navigate(RegistrationRoute.HireDate)
internal fun NavHostController.navigateToDayOff() = navigate(RegistrationRoute.DayOff)