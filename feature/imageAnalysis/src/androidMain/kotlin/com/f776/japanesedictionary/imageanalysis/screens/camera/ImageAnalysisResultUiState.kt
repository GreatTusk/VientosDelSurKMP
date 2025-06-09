package com.f776.japanesedictionary.imageanalysis.screens.camera

import android.graphics.Bitmap
import com.f776.core.common.LoadingState
import com.f776.japanesedictionary.domain.imageanalysis.ImageAnalysisResult

sealed interface ImageAnalysisUiState {
    data object Empty: ImageAnalysisUiState
    data class ImageSubmitted(val image: Bitmap, val ocrResult: LoadingState<ImageAnalysisResult>) : ImageAnalysisUiState
}