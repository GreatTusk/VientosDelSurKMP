package com.portafolio.vientosdelsur.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.f776.japanesedictionary.imageanalysis.navigation.imageAnalysisGraph
import com.f776.japanesedictionary.imageanalysis.navigation.navigateToImageAnalysis
import com.portafolio.vientosdelsur.feature.auth.navigation.AuthNavigation
import com.portafolio.vientosdelsur.feature.hotel.navigation.hotelGraph
import com.portafolio.vientosdelsur.feature.shift.navigation.shiftGraph
import com.portafolio.vientosdelsur.foryou.navigation.ForYou
import com.portafolio.vientosdelsur.foryou.navigation.forYouGraph

@Composable
fun TopLevelNavigationGraph(modifier: Modifier = Modifier, navController: NavHostController = rememberNavController()) {
    NavHost(modifier = modifier, navController = navController, startDestination = TopLevelNavigation) {
        navigation<TopLevelNavigation>(startDestination = ForYou) {
            forYouGraph(onNavigateToImageAnalysis = navController::navigateToImageAnalysis)
            hotelGraph()
            shiftGraph()
        }
        imageAnalysisGraph(onNavigateUp = navController::navigateUp)
    }
}

fun NavHostController.navigateToTopLevelNav() {
    navigate(TopLevelNavigation) {
        popUpTo(route = AuthNavigation) { inclusive = true }
        launchSingleTop = true
        restoreState = false
    }
}