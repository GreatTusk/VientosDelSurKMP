package com.portafolio.vientosdelsur.feature.auth.screens.signup.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.f776.core.ui.navigation.TransitionAnimation
import com.portafolio.vientosdelsur.feature.auth.screens.signup.EmployeeRegistrationData
import com.portafolio.vientosdelsur.feature.auth.screens.signup.steps.*
import kotlinx.datetime.DayOfWeek

@Composable
internal fun RegistrationNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    user: EmployeeRegistrationData?,
    onNavigationEvent: (RegistrationRoute) -> Unit,
    occupationOption: OccupationOption?,
    onOccupationSelected: (OccupationOption) -> Unit,
    formattedDate: String?,
    onDateSelected: (Long?) -> Unit,
    dayOff: DayOfWeek?,
    onDayOffSelected: (DayOfWeek) -> Unit
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

    NavHost(modifier = modifier, navController = navController, startDestination = RegistrationRoute.Profile) {
        composable<RegistrationRoute.Profile>(
            enterTransition = TransitionAnimation.FADE_SLIDE_RTL.enterTransition,
            exitTransition = TransitionAnimation.FADE_SLIDE_LTR.exitTransition,
            popEnterTransition = TransitionAnimation.FADE_SLIDE_LTR.popEnterTransition,
            popExitTransition = TransitionAnimation.FADE_SLIDE_RTL.popExitTransition
        ) {
            ProfileStep(
                onContinue = navController::navigateToOccupation,
                initialData = user
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

}