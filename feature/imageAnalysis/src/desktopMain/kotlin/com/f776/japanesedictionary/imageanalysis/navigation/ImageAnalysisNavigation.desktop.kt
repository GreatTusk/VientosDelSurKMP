package com.f776.japanesedictionary.imageanalysis.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.f776.core.ui.navigation.TransitionAnimation
import com.f776.japanesedictionary.imageanalysis.screens.fileSelector.OCRScreen
import com.f776.japanesedictionary.imageanalysis.screens.fileSelector.OCRViewModel
import org.koin.compose.viewmodel.koinViewModel

actual fun NavGraphBuilder.imageAnalysisGraph(onNavigateUp: () -> Unit) {
    composable<ImageAnalysisRoute>(
        enterTransition = { TransitionAnimation.FADE_SLIDE_RTL.enterTransition(this) },
        exitTransition = { TransitionAnimation.FADE_SLIDE_RTL.exitTransition(this) }
    ) {
        val viewModel: OCRViewModel = koinViewModel()
        OCRScreen(
            modifier = Modifier.fillMaxSize(),
            onGoBack = onNavigateUp,
            imagePainter = viewModel.imagePainter,
            onAction = viewModel::onAction
        )
    }
}