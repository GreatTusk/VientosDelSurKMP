package com.portafolio.vientosdelsur.feature.auth.screens.signup.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.f776.core.ui.navigation.TransitionAnimation
import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.feature.auth.screens.signup.steps.ProfileStep

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
        composable<RegistrationRoute.Profile>(
            enterTransition = TransitionAnimation.SLIDE_FADE.popEnterTransition,
            exitTransition = TransitionAnimation.SLIDE_FADE.exitTransition,
            popEnterTransition = TransitionAnimation.SLIDE_FADE.popEnterTransition,
            popExitTransition = TransitionAnimation.SLIDE_FADE.popExitTransition
        ) {
            ProfileStep(
                onContinue = navController::navigateToOccupation,
                initialData = user
            )
        }

        composable<RegistrationRoute.Occupation>(
            enterTransition = TransitionAnimation.SLIDE_FADE.enterTransition,
            exitTransition = TransitionAnimation.SLIDE_FADE.popExitTransition,
            popEnterTransition = TransitionAnimation.SLIDE_FADE.enterTransition,
            popExitTransition = TransitionAnimation.SLIDE_FADE.exitTransition
        ) {

        }
    }

}