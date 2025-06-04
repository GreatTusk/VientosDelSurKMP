package com.portafolio.vientosdelsur.feature.auth.screens.signup.navigation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.window.core.layout.WindowWidthSizeClass
import com.f776.core.ui.navigation.TransitionAnimation
import com.portafolio.vientosdelsur.feature.auth.screens.signup.ProfilePicture
import com.portafolio.vientosdelsur.feature.auth.screens.signup.steps.*
import kotlinx.datetime.DayOfWeek

@Composable
internal fun RegistrationNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onNavigationEvent: (RegistrationRoute) -> Unit,
    occupationOption: OccupationOption?,
    onOccupationSelected: (OccupationOption) -> Unit,
    formattedDate: String?,
    onDateSelected: (Long?) -> Unit,
    dayOff: DayOfWeek?,
    onDayOffSelected: (DayOfWeek) -> Unit,
    firstName: String,
    lastName: String,
    profilePicture: ProfilePicture,
    onFirstNameChanged: (String) -> Unit,
    onLastNameChanged: (String) -> Unit
) {
    val adaptiveInfo = currentWindowAdaptiveInfo()

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

                    hasRoute<RegistrationRoute.HireDate>() -> {
                        onNavigationEvent(RegistrationRoute.HireDate)
                    }

                    hasRoute<RegistrationRoute.DayOff>() -> {
                        onNavigationEvent(RegistrationRoute.DayOff)
                    }
                }
            }
        }
    }
    Row(modifier = modifier) {
        if (adaptiveInfo.windowSizeClass.windowWidthSizeClass != WindowWidthSizeClass.COMPACT) {
            Spacer(modifier = Modifier.weight(1.0f))
        }

        NavHost(
            modifier = if (adaptiveInfo.windowSizeClass.windowWidthSizeClass != WindowWidthSizeClass.COMPACT)
                Modifier.weight(2f)
            else Modifier,
            navController = navController,
            startDestination = RegistrationRoute.Profile
        ) {
            composable<RegistrationRoute.Profile>(
                enterTransition = TransitionAnimation.FADE_SLIDE_RTL.enterTransition,
                exitTransition = TransitionAnimation.FADE_SLIDE_LTR.exitTransition,
                popEnterTransition = TransitionAnimation.FADE_SLIDE_LTR.popEnterTransition,
                popExitTransition = TransitionAnimation.FADE_SLIDE_RTL.popExitTransition
            ) {
                ProfileStep(
                    onContinue = navController::navigateToOccupation,
                    onFirstNameChanged = onFirstNameChanged,
                    onLastNameChanged = onLastNameChanged,
                    firstName = firstName,
                    lastName = lastName,
                    profilePicture = profilePicture
                )
            }

            composable<RegistrationRoute.Occupation>(
                enterTransition = TransitionAnimation.FADE_SLIDE_RTL.enterTransition,
                exitTransition = TransitionAnimation.FADE_SLIDE_LTR.exitTransition,
                popEnterTransition = TransitionAnimation.FADE_SLIDE_LTR.popEnterTransition,
                popExitTransition = TransitionAnimation.FADE_SLIDE_RTL.popExitTransition
            ) {
                OccupationStep(
                    onContinue = navController::navigateToHireDate,
                    occupationOption = occupationOption,
                    onOccupationSelected = onOccupationSelected
                )
            }

            composable<RegistrationRoute.HireDate>(
                enterTransition = TransitionAnimation.FADE_SLIDE_RTL.enterTransition,
                exitTransition = TransitionAnimation.FADE_SLIDE_LTR.exitTransition,
                popEnterTransition = TransitionAnimation.FADE_SLIDE_LTR.popEnterTransition,
                popExitTransition = TransitionAnimation.FADE_SLIDE_RTL.popExitTransition
            ) {
                HireDateStep(
                    onContinue = navController::navigateToDayOff,
                    formattedDate = formattedDate,
                    onDateSelected = onDateSelected
                )
            }

            composable<RegistrationRoute.DayOff>(
                enterTransition = TransitionAnimation.FADE_SLIDE_RTL.enterTransition,
                exitTransition = TransitionAnimation.FADE_SLIDE_LTR.exitTransition,
                popEnterTransition = TransitionAnimation.FADE_SLIDE_LTR.popEnterTransition,
                popExitTransition = TransitionAnimation.FADE_SLIDE_RTL.popExitTransition
            ) {
                DayOffStep(
                    onContinue = {},
                    dayOfWeek = dayOff,
                    onDayOfWeekSelected = onDayOffSelected
                )
            }
        }

        if (adaptiveInfo.windowSizeClass.windowWidthSizeClass != WindowWidthSizeClass.COMPACT) {
            Spacer(modifier = Modifier.weight(1.0f))
        }
    }


}
