package com.portafolio.vientosdelsur.feature.auth.screens.signup.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.feature.auth.screens.signup.steps.ProfileStep
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

private fun NavHostController.navigateToProfile() = navigate(RegistrationRoute.Profile)
private fun NavHostController.navigateToOccupation() = navigate(RegistrationRoute.Occupation)

@Composable
internal fun RegistrationNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    user: Employee?,
    onNavigationEvent: (RegistrationRoute) -> Unit
) {
    LaunchedEffect(Unit) {
        navController.currentBackStackEntryFlow.collect {
            with(it.destination) {
                when {
                    hasRoute<RegistrationRoute.Profile>() -> {
                        onNavigationEvent(RegistrationRoute.Profile)
                    }

                    hasRoute<RegistrationRoute.Occupation>() -> {
                        onNavigationEvent(RegistrationRoute.Occupation)
                    }
                }
            }
        }
    }

    NavHost(modifier = modifier, navController = navController, startDestination = RegistrationRoute.Profile) {
        composable<RegistrationRoute.Profile> {
            ProfileStep(
                onContinue = navController::navigateToOccupation,
                initialData = user
            )
        }

        composable<RegistrationRoute.Occupation> {

        }
    }
}