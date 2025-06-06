package com.f776.japanesedictionary.imageanalysis.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import kotlinx.serialization.Serializable

@Serializable
data object ImageAnalysisRoute

fun NavController.navigateToImageAnalysis() {
    navigate(ImageAnalysisRoute) {
        launchSingleTop = true
    }
}

expect fun NavGraphBuilder.imageAnalysisGraph(
    onNavigateUp: () -> Unit,
)