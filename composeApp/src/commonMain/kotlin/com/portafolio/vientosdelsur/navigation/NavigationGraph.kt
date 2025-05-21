package com.portafolio.vientosdelsur.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.portafolio.vientosdelsur.feature.auth.navigation.AuthNavigation
import com.portafolio.vientosdelsur.feature.auth.navigation.authGraph
import com.portafolio.vientosdelsur.feature.hotel.navigation.hotelGraph
import com.portafolio.vientosdelsur.foryou.navigation.ForYou
import com.portafolio.vientosdelsur.foryou.navigation.forYouGraph

@Composable
fun NavigationGraph(modifier: Modifier = Modifier, navController: NavHostController = rememberNavController()) {
    NavHost(modifier = modifier, navController = navController, startDestination = AuthNavigation) {
        authGraph(navController::navigateToTopLevelNav)
        navigation<TopLevelNavigation>(startDestination = ForYou) {
            forYouGraph()
            hotelGraph()
        }
    }
}

fun NavHostController.navigateToTopLevelNav() {
    navigate(TopLevelNavigation) {
        popUpTo(route = AuthNavigation) { inclusive = true }
        launchSingleTop = true
        restoreState = false
    }
}