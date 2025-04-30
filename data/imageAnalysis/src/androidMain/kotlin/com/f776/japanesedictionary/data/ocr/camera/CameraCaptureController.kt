package com.f776.japanesedictionary.data.ocr.camera

import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Log
import android.util.Size
import androidx.camera.core.ImageCapture
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.core.resolutionselector.ResolutionStrategy
import androidx.camera.core.takePicture
import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.f776.japanesedictionary.domain.ocr.ImageDimension
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext

class CameraCaptureController(private val ioDispatcher: CoroutineDispatcher) {
    val cameraCaptureUseCase: ImageCapture = ImageCapture.Builder().setResolutionSelector(
        ResolutionSelector.Builder()
            .setResolutionStrategy(
                ResolutionStrategy(
                    Size(ImageDimension.IMAGE_WIDTH, ImageDimension.IMAGE_HEIGHT),
                    ResolutionStrategy.FALLBACK_RULE_CLOSEST_LOWER_THEN_HIGHER
                )
            )
            .build()
    ).build()

    suspend fun takePhoto(): Result<Bitmap, DataError.Local> = withContext(ioDispatcher) {
        try {
            cameraCaptureUseCase.takePicture().use { imageProxy ->
                Log.i("takePhoto", "${imageProxy.width}x${imageProxy.height}")

                val matrix = Matrix().apply {
                    val rotDegrees = imageProxy.imageInfo.rotationDegrees.toFloat()
                    Log.i("takePhoto", "Rotating by $rotDegrees")
                    postRotate(rotDegrees)
                }

                // Convert to bitmap and apply rotation
                val bitmap = Bitmap.createBitmap(
                    imageProxy.toBitmap(),
                    0,
                    0,
                    imageProxy.width,
                    imageProxy.height,
                    matrix,
                    true
                )

                Result.Success(bitmap)
            }
        } catch (_: Exception) {
            coroutineContext.ensureActive()
            Result.Error(DataError.Local.UNKNOWN)
        }
    }
}