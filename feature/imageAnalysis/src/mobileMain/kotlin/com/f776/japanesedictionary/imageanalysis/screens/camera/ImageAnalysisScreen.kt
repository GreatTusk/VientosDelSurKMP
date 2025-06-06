package com.f776.japanesedictionary.imageanalysis.screens.camera

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun ImageAnalysisScreenRoot(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit
) {
    val factory = rememberPermissionsControllerFactory()
    val controller = remember(factory) {
        factory.createPermissionsController()
    }

    BindEffect(controller)

    viewModel {
        CameraPermissionViewModel(permissionsController = controller)
    }

    ImageAnalysisScreen(
        modifier = modifier,
        onNavigateUp = onNavigateUp,
        imageAnalysisViewModel = koinViewModel()
    )
}


@Composable
internal expect fun ImageAnalysisScreen(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit,
    imageAnalysisViewModel: ImageAnalysisViewModel = koinViewModel()
)