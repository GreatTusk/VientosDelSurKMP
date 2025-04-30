package com.f776.japanesedictionary.ocr.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.f776.core.ui.navigation.TransitionAnimation
import com.f776.japanesedictionary.ocr.screens.camera.OCRScreenRoot
import kotlinx.serialization.Serializable

@Serializable
data class OCRRoute(val imageUri: String? = null)

fun NavController.navigateToOcr(imageUri: String? = null) {
    navigate(OCRRoute(imageUri)) {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.ocrGraph(
    onNavigateUp: () -> Unit,
    onNavigateToTranslation: (String?) -> Unit,
    onNavigateToDictionary: (Int?, String?, String?) -> Unit
) {
    composable<OCRRoute>(
        enterTransition = { TransitionAnimation.FADE_SLIDE_RTL.enterTransition(this) },
        exitTransition = { TransitionAnimation.FADE_SLIDE_RTL.exitTransition(this) }
    ) {
        OCRScreenRoot(
            modifier = Modifier.fillMaxSize(),
            onNavigateUp = onNavigateUp,
            onNavigateToTranslation = onNavigateToTranslation,
            onNavigateToDictionary = {
                onNavigateToDictionary(null, null, it)
            }
        )
    }
}