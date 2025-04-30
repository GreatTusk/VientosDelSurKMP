package com.f776.japanesedictionary.ocr.screens.camera

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun OCRScreenRoot(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit,
    onNavigateToDictionary: (String) -> Unit,
    onNavigateToTranslation: (String) -> Unit
) {
    val factory = rememberPermissionsControllerFactory()
    val controller = remember(factory) {
        factory.createPermissionsController()
    }

    BindEffect(controller)

    viewModel {
        CameraPermissionViewModel(permissionsController = controller)
    }

    OCRScreen(
        modifier = modifier,
        onNavigateUp = onNavigateUp,
        onNavigateToTranslation = onNavigateToTranslation,
        onNavigateToDictionary = onNavigateToDictionary,
        ocrViewModel = koinViewModel()
    )
}


@Composable
internal expect fun OCRScreen(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit,
    onNavigateToTranslation: (String) -> Unit,
    onNavigateToDictionary: (String) -> Unit,
    ocrViewModel: OCRViewModel = koinViewModel()
)