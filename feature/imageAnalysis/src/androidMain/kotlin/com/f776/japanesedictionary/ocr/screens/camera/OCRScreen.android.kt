package com.f776.japanesedictionary.ocr.screens.camera

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
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
import com.f776.japanesedictionary.ocr.screens.camera.feed.CameraFeed
import com.f776.japanesedictionary.ocr.screens.camera.overlay.CameraControlsOverlay
import com.f776.japanesedictionary.ocr.screens.camera.overlay.FocusBoxOverlay
import com.f776.japanesedictionary.ocr.screens.camera.overlay.OCRResultOverlay
import com.f776.japanesedictionary.ocr.screens.camera.overlay.TopBarOverlay

@Composable
internal actual fun OCRScreen(
    modifier: Modifier,
    onNavigateUp: () -> Unit,
    onNavigateToTranslation: (String) -> Unit,
    onNavigateToDictionary: (String) -> Unit,
    ocrViewModel: OCRViewModel
) {
    val appContext = LocalContext.current.applicationContext
    val uiState by ocrViewModel.uiState.collectAsStateWithLifecycle()

    val pickMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            // Callback is invoked after the user selects a media item or closes the
            // photo picker.
            if (uri != null) {
                ocrViewModel.onImageSelected(uri, appContext)
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }

    BoxWithConstraints(modifier = modifier) {
        val focusBoxSize = remember(maxWidth, maxHeight) {
            minOf(maxWidth, maxHeight) * 0.7f
        }

        when (val state = uiState) {
            OCRUiState.Empty -> {
                CameraFeed(ocrViewModel)
                FocusBoxOverlay(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(focusBoxSize)
                )
            }

            is OCRUiState.ImageSubmitted -> {
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
                        OCRResultOverlay(
                            modifier = Modifier
                                .aspectRatio(state.image.width.toFloat() / state.image.height.toFloat())
                                .align(Alignment.Center)
                                .fillMaxSize()
                                .graphicsLayer {
                                    val scale =
                                        this@BoxWithConstraints.maxHeight.toPx() / this.size.height

                                    Log.i(
                                        "GraphicsLayerScope",
                                        "Box size ${this@BoxWithConstraints.maxWidth.toPx()}x${this@BoxWithConstraints.maxHeight.toPx()}"
                                    )
                                    Log.i(
                                        "GraphicsLayerScope",
                                        "Overlay size ${this.size.width}x${this.size.height}"
                                    )
                                    Log.i("GraphicsLayerScope", "Scale applied: $scale")

                                    scaleX = scale
                                    scaleY = scale
                                },
                            detectedText = state.ocrResult.data,
                            originalImageWidth = state.image.width,
                            originalImageHeight = state.image.height
                        )
                    }
                }
            }
        }

        TopBarOverlay(onNavigateUp = onNavigateUp)

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
            onNavigateToTranslation = {
                val state = uiState
                if (state is OCRUiState.ImageSubmitted && state.ocrResult is LoadingState.Success) {
                    onNavigateToTranslation(state.ocrResult.data.text)
                }
            },
            onImageCaptured = ocrViewModel::onImageCaptured,
            onNavigateToDictionary = {
                val state = uiState
                if (state is OCRUiState.ImageSubmitted && state.ocrResult is LoadingState.Success) {
                    onNavigateToDictionary(state.ocrResult.data.text)
                }
            },
            showCamera = uiState == OCRUiState.Empty
        )
    }
}