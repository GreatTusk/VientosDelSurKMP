package com.f776.japanesedictionary.imageanalysis.screens.camera

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.f776.core.common.LoadingState
import com.f776.japanesedictionary.imageanalysis.components.ImageAnalysisResultDialog
import com.f776.japanesedictionary.imageanalysis.screens.camera.feed.CameraFeed
import com.f776.japanesedictionary.imageanalysis.screens.camera.overlay.CameraControlsOverlay
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

    var showResultDialog by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(uiState) {
        val ready = (uiState as? ImageAnalysisUiState.ImageSubmitted)?.imageAnalysisResult as? LoadingState.Success
        if (ready != null) {
            showResultDialog = true
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = { TopBarOverlay(onNavigateUp = onNavigateUp) },
        containerColor = MaterialTheme.colorScheme.surfaceContainer
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (val state = uiState) {
                ImageAnalysisUiState.Empty -> {
                    CameraFeed(
                        viewModel = imageAnalysisViewModel,
                        modifier = Modifier.clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
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

                    when (state.imageAnalysisResult) {
                        LoadingState.Loading -> {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }

                        is LoadingState.Success -> {
                            if (showResultDialog) {
                                ImageAnalysisResultDialog(
                                    result = state.imageAnalysisResult.data,
                                    onDismissRequest = { showResultDialog = false }
                                )
                            }
                        }

                        else -> {}
                    }
                }
            }


            CameraControlsOverlay(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(12.dp),
                onOpenGallery = {
                    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                },
                onImageCaptured = imageAnalysisViewModel::onImageCaptured,
                showCamera = uiState == ImageAnalysisUiState.Empty
            )
        }

    }
}