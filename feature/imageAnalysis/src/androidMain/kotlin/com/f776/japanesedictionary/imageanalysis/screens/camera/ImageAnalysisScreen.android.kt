package com.f776.japanesedictionary.imageanalysis.screens.camera

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.f776.japanesedictionary.imageanalysis.components.RoomSelectionDialog
import com.f776.japanesedictionary.imageanalysis.model.RoomSelectionUi
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
    val rooms by imageAnalysisViewModel.rooms.collectAsStateWithLifecycle()
    val selectedRoom = imageAnalysisViewModel.selectedRoom

    val pickMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                imageAnalysisViewModel.onImageSelected(uri, appContext)
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }

    var showResultDialog by rememberSaveable { mutableStateOf(false) }
    var showSelectRoomDialog by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(uiState) {
        val ready = (uiState as? ImageAnalysisUiState.ImageSubmitted)?.imageAnalysisResult as? LoadingState.Success
        if (ready != null) {
            showResultDialog = true
        }
    }

    ImageAnalysisContent(
        modifier = modifier,
        uiState = uiState,
        rooms = rooms,
        selectedRoom = selectedRoom,
        showResultDialog = showResultDialog,
        showSelectRoomDialog = showSelectRoomDialog,
        onNavigateUp = onNavigateUp,
        onOpenGallery = {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        },
        onImageCaptured = imageAnalysisViewModel::onImageCaptured,
        onRoomSelection = { showSelectRoomDialog = true },
        onRoomSelected = imageAnalysisViewModel::onRoomSelected,
        onDismissResultDialog = { showResultDialog = false },
        onDismissRoomDialog = { showSelectRoomDialog = false },
        imageAnalysisViewModel = imageAnalysisViewModel
    )
}

@Composable
private fun ImageAnalysisContent(
    modifier: Modifier,
    uiState: ImageAnalysisUiState,
    rooms: List<RoomSelectionUi>,
    selectedRoom: RoomSelectionUi?,
    showResultDialog: Boolean,
    showSelectRoomDialog: Boolean,
    onNavigateUp: () -> Unit,
    onOpenGallery: () -> Unit,
    onImageCaptured: () -> Unit,
    onRoomSelection: () -> Unit,
    onRoomSelected: (RoomSelectionUi) -> Unit,
    onDismissResultDialog: () -> Unit,
    onDismissRoomDialog: () -> Unit,
    imageAnalysisViewModel: ImageAnalysisViewModel
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopBarOverlay(
                onNavigateUp = onNavigateUp,
                selectedRoomSelectionUi = selectedRoom
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceContainer
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (uiState) {
                ImageAnalysisUiState.Empty -> {
                    CameraFeed(
                        viewModel = imageAnalysisViewModel,
                        modifier = Modifier.clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                    )

                    if (showSelectRoomDialog) {
                        RoomSelectionDialog(
                            rooms = rooms,
                            selectedRoom = selectedRoom,
                            onRoomSelected = onRoomSelected,
                            onDismissRequest = onDismissRoomDialog
                        )
                    }
                }

                is ImageAnalysisUiState.ImageSubmitted -> {
                    Image(
                        bitmap = uiState.image.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillHeight
                    )

                    when (uiState.imageAnalysisResult) {
                        LoadingState.Loading -> {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }

                        is LoadingState.Success -> {
                            if (showResultDialog) {
                                ImageAnalysisResultDialog(
                                    result = uiState.imageAnalysisResult.data,
                                    onDismissRequest = onDismissResultDialog
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
                onOpenGallery = onOpenGallery,
                onImageCaptured = onImageCaptured,
                onRoomSelection = onRoomSelection,
                isRoomSelected = selectedRoom != null
            )
        }
    }
}