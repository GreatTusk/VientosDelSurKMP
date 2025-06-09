package com.f776.japanesedictionary.imageanalysis.screens.camera

import android.app.Application
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
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f776.core.common.LoadingState
import com.f776.core.common.onEmpty
import com.f776.core.common.onError
import com.f776.core.common.onSuccess
import com.f776.japanesedictionary.data.imageanalysis.analyzer.OCRAnalyzer
import com.f776.japanesedictionary.data.imageanalysis.camera.CameraCaptureController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal actual class ImageAnalysisViewModel(
    private val ocrAnalyzer: OCRAnalyzer,
    private val cameraCaptureController: CameraCaptureController,
    savedStateHandle: SavedStateHandle,
    application: Application
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
            ocrAnalyzer.analyze(bitmap = bitmap)
            .onSuccess { result ->
//                _uiState.update { OCRUiState.ImageSubmitted(bitmap, LoadingState.Success(result)) }
                _uiState.update {
                    (it as ImageAnalysisUiState.ImageSubmitted).copy(
                        ocrResult = LoadingState.Success(result)
                    )
                }
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