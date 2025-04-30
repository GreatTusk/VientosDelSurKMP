package com.f776.japanesedictionary.ocr.screens.camera

import android.graphics.Bitmap
import com.f776.core.common.LoadingState
import com.f776.japanesedictionary.domain.ocr.OCRResult

sealed interface OCRUiState {
    data object Empty: OCRUiState
    data class ImageSubmitted(val image: Bitmap, val ocrResult: LoadingState<OCRResult>) : OCRUiState
}

//internal data class OCRResultUiState(
//    val image: LoadingState<Bitmap>,
//    val ocrResult: LoadingState<OCRResult> = LoadingState.InProgress
//)