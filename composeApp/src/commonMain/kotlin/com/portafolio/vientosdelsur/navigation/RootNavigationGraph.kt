package com.portafolio.vientosdelsur.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.f776.core.ui.components.ObserveAsEvents
import com.portafolio.vientosdelsur.AppViewModel
import com.portafolio.vientosdelsur.AuthEvent
import com.portafolio.vientosdelsur.domain.auth.User
import com.portafolio.vientosdelsur.feature.auth.navigation.AuthNavigation
import com.portafolio.vientosdelsur.feature.auth.navigation.authGraph
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RootNavigationGraph(navHostController: NavHostController = rememberNavController()) {
    val appViewModel = koinViewModel<AppViewModel>()
    val user by appViewModel.user.collectAsStateWithLifecycle()

    ObserveAsEvents(appViewModel.events) {
        when (it) {
            is AuthEvent.OnUserAuthenticated -> navHostController.navigateToTopLevelNav()
            AuthEvent.OnUserLoggedOut -> navHostController.navigateToAuth()
        }
    }

    NavHost(navController = navHostController, startDestination = AuthNavigation) {
        authGraph(navHostController::navigateToTopLevelNav)
        composable<TopLevelNavigation> {
            TopLevelNavigation(navController = rememberNavController())
        }
    }
}

fun NavHostController.navigateToAuth() {
    navigate(AuthNavigation) {
        popUpTo(route = TopLevelNavigation) { inclusive = true }
        launchSingleTop = true
        restoreState = false
    }
}