package com.f776.japanesedictionary.imageanalysis.screens.camera

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.camera.core.CameraSelector.DEFAULT_BACK_CAMERA
import androidx.camera.core.Preview
import androidx.camera.core.SurfaceRequest
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.lifecycle.awaitInstance
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f776.core.common.*
import com.f776.japanesedictionary.data.imageanalysis.camera.CameraCaptureController
import com.f776.japanesedictionary.domain.imageanalysis.ImageAnalysisService
import com.f776.japanesedictionary.imageanalysis.model.RoomSelectionUi
import com.f776.japanesedictionary.imageanalysis.model.toRoomSelectionUi
import com.portafolio.vientosdelsur.core.mediapicker.data.toByteArray
import com.portafolio.vientosdelsur.domain.room.RoomRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

internal actual class ImageAnalysisViewModel(
    private val imageAnalysisService: ImageAnalysisService,
    private val cameraCaptureController: CameraCaptureController,
    private val roomRepository: RoomRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<ImageAnalysisUiState>(ImageAnalysisUiState.Empty)
    val uiState = _uiState.asStateFlow()

    private val _surfaceRequest = MutableStateFlow<SurfaceRequest?>(null)
    val surfaceRequest = _surfaceRequest.asStateFlow()

    private val cameraPreviewUseCase = Preview.Builder().build().apply {
        setSurfaceProvider { newSurfaceRequest ->
            _surfaceRequest.update { newSurfaceRequest }
        }
    }

    val rooms = flow { emit(roomRepository.getAllRooms().takeOrNull()) }
        .mapNotNull { rooms -> rooms?.map { it.toRoomSelectionUi() } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5.seconds),
            initialValue = emptyList()
        )

    var selectedRoom by mutableStateOf<RoomSelectionUi?>(null)
        private set

    fun onRoomSelected(roomSelectionUi: RoomSelectionUi) {
        selectedRoom = roomSelectionUi
    }

    suspend fun bindToCamera(appContext: Context, lifecycleOwner: LifecycleOwner) {
        val processCameraProvider = ProcessCameraProvider.awaitInstance(appContext)
        processCameraProvider.bindToLifecycle(
            lifecycleOwner,
            DEFAULT_BACK_CAMERA,
            cameraPreviewUseCase,
            cameraCaptureController.cameraCaptureUseCase
        )

        // Cancellation signals we're done with the camera
        try {
            awaitCancellation()
        } finally {
            processCameraProvider.unbindAll()
        }
    }


    // First the bitmap is available, then the results
    private suspend fun analyze(bitmap: Bitmap) {
        _uiState.update { ImageAnalysisUiState.ImageSubmitted(bitmap, LoadingState.Loading) }
        imageAnalysisService.classifyImage(
            byteArray = bitmap.asImageBitmap().toByteArray(),
            roomId = checkNotNull(selectedRoom?.id) { Log.wtf("ImageAnalysisViewModel", "Room cannot be null") }
        )
            .onSuccess { result ->
                _uiState.update {
                    (it as ImageAnalysisUiState.ImageSubmitted).copy(
                        imageAnalysisResult = LoadingState.Success(result)
                    )
                }
                Log.i("ImageAnalysisViewModel", result.name)
            }
            .onEmpty {
                _uiState.update { ImageAnalysisUiState.ImageSubmitted(bitmap, LoadingState.Empty) }
            }
            .onError { error ->
                _uiState.update { ImageAnalysisUiState.ImageSubmitted(bitmap, LoadingState.Error(error)) }
            }
    }

    fun onImageSelected(uri: Uri, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val inputStream = context.contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            analyze(bitmap)
        }
    }

    fun onImageCaptured() {
        viewModelScope.launch {
            cameraCaptureController.takePhoto()
                .onSuccess {
                    analyze(it)
                }
                .onError {
                    Log.e("OCRViewModel", "Error taking photo")
                }
        }
    }
}