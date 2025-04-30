package com.f776.japanesedictionary.ocr.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.f776.core.ui.navigation.TransitionAnimation
import com.f776.japanesedictionary.ocr.screens.fileSelector.OCRScreen
import com.f776.japanesedictionary.ocr.screens.fileSelector.OCRViewModel
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

@Serializable
data object OCRRoute

fun NavController.navigateToOcr() {
    navigate(OCRRoute) {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.ocrGraph(onGoBack: () -> Unit) {
    composable<OCRRoute>(
        enterTransition = { TransitionAnimation.FADE_SLIDE_RTL.enterTransition(this) },
        exitTransition = { TransitionAnimation.FADE_SLIDE_RTL.exitTransition(this) }
    ) {
        val viewModel: OCRViewModel = koinViewModel()
        OCRScreen(
            modifier = Modifier.fillMaxSize(),
            onGoBack = onGoBack,
            imagePainter = viewModel.imagePainter,
            onAction = viewModel::onAction
        )
    }
}