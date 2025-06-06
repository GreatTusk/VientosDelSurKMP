package com.f776.japanesedictionary.imageanalysis.screens.camera

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.f776.core.common.LoadingState
import com.f776.japanesedictionary.imageanalysis.screens.camera.feed.CameraFeed
import com.f776.japanesedictionary.imageanalysis.screens.camera.overlay.CameraControlsOverlay
import com.f776.japanesedictionary.imageanalysis.screens.camera.overlay.FocusBoxOverlay
import com.f776.japanesedictionary.imageanalysis.screens.camera.overlay.OCRResultOverlay
import com.f776.japanesedictionary.imageanalysis.screens.camera.overlay.TopBarOverlay

@Composable
internal actual fun ImageAnalysisScreen(
    modifier: Modifier,
    onNavigateUp: () -> Unit,
    imageAnalysisViewModel: ImageAnalysisViewModel
) {
    val appContext = LocalContext.current.applicationContext
    val uiState by imageAnalysisViewModel.uiState.collectAsStateWithLifecycle()

    val pickMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            // Callback is invoked after the user selects a media item or closes the
            // photo picker.
            if (uri != null) {
                imageAnalysisViewModel.onImageSelected(uri, appContext)
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }

    Scaffold(
        modifier = modifier,
        topBar = { TopBarOverlay(onNavigateUp = onNavigateUp) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (val state = uiState) {
                ImageAnalysisUiState.Empty -> {
                    CameraFeed(
                        imageAnalysisViewModel,
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.TopCenter)
                    )
                }

                is ImageAnalysisUiState.ImageSubmitted -> {
                    Image(
                        bitmap = state.image.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.FillHeight
                    )

                    when (state.ocrResult) {
                        LoadingState.Empty, is LoadingState.Error, LoadingState.Loading -> {}
                        is LoadingState.Success -> {

                        }
                    }
                }
            }


            CameraControlsOverlay(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(
                        bottom = WindowInsets.systemBars.asPaddingValues()
                            .calculateBottomPadding() + 12.dp
                    ),
                onOpenGallery = {
                    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                },
                onImageCaptured = imageAnalysisViewModel::onImageCaptured,
                showCamera = uiState == ImageAnalysisUiState.Empty
            )
        }

    }
}