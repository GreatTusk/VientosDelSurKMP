package com.f776.japanesedictionary.imageanalysis.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.f776.core.ui.navigation.TransitionAnimation
import com.f776.japanesedictionary.imageanalysis.screens.camera.ImageAnalysisScreenRoot
import kotlinx.serialization.Serializable

@Serializable
data class ImageAnalysisRoute(val imageUri: String? = null)

fun NavController.navigateToImageAnalysis(imageUri: String? = null) {
    navigate(ImageAnalysisRoute(imageUri)) {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.imageAnalysisGraph(
    onNavigateUp: () -> Unit,
) {
    composable<ImageAnalysisRoute>(
        enterTransition = { TransitionAnimation.FADE_SLIDE_RTL.enterTransition(this) },
        exitTransition = { TransitionAnimation.FADE_SLIDE_RTL.exitTransition(this) }
    ) {
        ImageAnalysisScreenRoot(
            modifier = Modifier.fillMaxSize(),
            onNavigateUp = onNavigateUp,
        )
    }
}