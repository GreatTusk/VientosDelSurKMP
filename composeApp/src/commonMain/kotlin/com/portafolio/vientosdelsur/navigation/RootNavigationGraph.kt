package com.portafolio.vientosdelsur.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.portafolio.vientosdelsur.feature.auth.navigation.AuthNavigation
import com.portafolio.vientosdelsur.feature.auth.navigation.authGraph

@Composable
fun RootNavigationGraph(navHostController: NavHostController = rememberNavController()) {
    NavHost(navController = navHostController, startDestination = AuthNavigation) {
        authGraph(navHostController::navigateToTopLevelNav)
        composable<TopLevelNavigation> {
            TopLevelNavigation(navController = rememberNavController())
        }
    }
}