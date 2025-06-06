package com.portafolio.vientosdelsur.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.f776.core.ui.components.ObserveAsEvents
import com.f776.japanesedictionary.imageanalysis.navigation.ImageAnalysisRoute
import com.f776.japanesedictionary.imageanalysis.navigation.imageAnalysisGraph
import com.portafolio.vientosdelsur.AppViewModel
import com.portafolio.vientosdelsur.AuthEvent
import com.portafolio.vientosdelsur.feature.auth.navigation.AuthNavigation
import com.portafolio.vientosdelsur.feature.auth.navigation.authGraph
import com.portafolio.vientosdelsur.feature.auth.navigation.navigateToRegistration
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RootNavigationGraph(navHostController: NavHostController = rememberNavController()) {
    val appViewModel = koinViewModel<AppViewModel>()
    val user by appViewModel.user.collectAsStateWithLifecycle()
    val backstack by navHostController.currentBackStackEntryAsState()

    ObserveAsEvents(appViewModel.events) {
        when (it) {
            is AuthEvent.OnUserAuthenticated -> navHostController.navigateToTopLevelNav()
            AuthEvent.OnUserLoggedOut -> {
                if (backstack?.destination?.hasRoute<TopLevelNavigation>() == true) {
                    navHostController.navigateToAuth()
                }
            }

            is AuthEvent.OnRegistrationPending -> navHostController.navigateToRegistration(
                userId = it.user.id,
                profilePictureUrl = it.user.photoUrl,
                userName = it.user.name,
                email = it.user.email.email
            )
        }
    }

    NavHost(navController = navHostController, startDestination = AuthNavigation) {
        authGraph(navHostController::navigateToTopLevelNav)
        composable<TopLevelNavigation> {
            TopLevelNavigation(navController = rememberNavController())
        }
        imageAnalysisGraph(onNavigateUp = navHostController::navigateUp)
    }
}

fun NavHostController.navigateToAuth() {
    navigate(AuthNavigation) {
        popUpTo(route = TopLevelNavigation) { inclusive = true }
        launchSingleTop = true
        restoreState = false
    }
}