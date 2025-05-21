package com.portafolio.vientosdelsur.foryou.navigation

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.portafolio.vientosdelsur.foryou.screens.foryou.ForYouScreenRoot
import kotlinx.serialization.Serializable

@Serializable
data object ForYou

fun NavController.navigateToForYou(navOptions: NavOptions? = null) = navigate(route = ForYou, navOptions)

fun NavGraphBuilder.forYouGraph() {
    composable<ForYou> {
        ForYouScreenRoot()
    }
}