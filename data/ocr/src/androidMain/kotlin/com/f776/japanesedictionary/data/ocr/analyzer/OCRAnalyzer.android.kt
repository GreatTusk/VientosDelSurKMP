package com.f776.japanesedictionary.data.ocr.analyzer

import android.graphics.Bitmap
import android.util.Log
import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.f776.japanesedictionary.data.ocr.mapper.toDomainPoint
import com.f776.japanesedictionary.data.ocr.mapper.toDomainRect
import com.f776.japanesedictionary.domain.ocr.OCRLine
import com.f776.japanesedictionary.domain.ocr.OCRResult
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.japanese.JapaneseTextRecognizerOptions
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

actual class OCRAnalyzer(
    private val textRecognizer: TextRecognizer = TextRecognition.getClient(
        JapaneseTextRecognizerOptions.Builder().build()
    ),
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun analyze(bitmap: Bitmap): Result<OCRResult, DataError.Local> =
        withContext(ioDispatcher) {
            val inputImage = InputImage.fromBitmap(bitmap, 0)
            Log.i("OCRAnalyzer", "${inputImage.width} x ${inputImage.height}")

            val detectedText = try {
                textRecognizer.process(inputImage).await()
            } catch (e: Exception) {
                coroutineContext.ensureActive()
                return@withContext Result.Error(DataError.Local.UNKNOWN)
            }

            val detectedBlocks = detectedText.textBlocks

            if (detectedBlocks.isEmpty()) {
                return@withContext Result.Empty
            }

            val textLines = detectedBlocks.flatMap { it.lines }.map { line ->
                Log.i("OCRAnalyzer", "Detected line: ${line.boundingBox}")
                OCRLine(
                    text = line.text,
                    boundingBox = line.boundingBox?.toDomainRect()
                        ?: error("Bounding box is null"),
                    recognizedLanguage = line.recognizedLanguage,
                    points = line.cornerPoints?.map { it.toDomainPoint() }?.toTypedArray()
                        ?: emptyArray()
                )
            }

            Result.Success(
                OCRResult(
                    text = detectedText.text,
                    textLines = textLines
                )
            )
        }
}