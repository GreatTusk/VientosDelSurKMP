package com.portafolio.vientosdelsur.foryou.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.portafolio.vientosdelsur.foryou.screens.foryou.navigation.OccupationNavigation
import kotlinx.serialization.Serializable

@Serializable
data object ForYou

fun NavController.navigateToForYou(navOptions: NavOptions? = null) = navigate(route = ForYou, navOptions)

fun NavGraphBuilder.forYouGraph(onNavigateToImageAnalysis: () -> Unit) {
    composable<ForYou> {
        OccupationNavigation(onNavigateToImageAnalysis = onNavigateToImageAnalysis)
    }
}