package com.f776.japanesedictionary.data.ocr.analyzer

import android.graphics.Bitmap
import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.f776.japanesedictionary.domain.imageanalysis.OCRResult
import kotlinx.coroutines.CoroutineDispatcher

actual class OCRAnalyzer(
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun analyze(bitmap: Bitmap): Result<OCRResult, DataError.Local> = Result.Empty
}