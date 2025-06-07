package com.portafolio.vientosdelsur.data.imageanalysis.repository

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.microsoft.azure.cognitiveservices.vision.customvision.prediction.CustomVisionPredictionClient
import com.portafolio.vientosdelsur.data.imageanalysis.mapper.toImageAnalysisResult
import com.portafolio.vientosdelsur.data.imageanalysis.util.asFlow
import com.portafolio.vientosdelsur.domain.imageanalysis.ImageAnalysisRepository
import com.portafolio.vientosdelsur.domain.imageanalysis.ImageAnalysisResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.single
import java.util.*

internal class AzureImageAnalysisRepository(
    private val predictor: CustomVisionPredictionClient,
    private val ioDispatcher: CoroutineDispatcher
) : ImageAnalysisRepository {
    override suspend fun analyze(imageBytes: ByteArray): Result<Set<ImageAnalysisResult>, DataError.Remote> =
        try {
            val results = predictor.predictions()
                .classifyImageAsync(
                    /* projectId = */ PROJECT_ID,
                    /* publishedName = */ MODEL_NAME,
                    /* imageData = */ imageBytes,
                    /* classifyImageOptionalParameter = */ null
                )
                .asFlow()
                .flowOn(ioDispatcher)
                .single()


            Result.Success(results.predictions().map { it.toImageAnalysisResult() }.toSet())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(DataError.Remote.LOGICAL)
        }

    companion object {
        private val PROJECT_ID =
            UUID.fromString(System.getenv("CUSTOM_VISION_PROJECT_ID")) ?: error("Missing CUSTOM_VISION_PROJECT_ID")
        private val MODEL_NAME = System.getenv("CUSTOM_VISION_MODEL_NAME") ?: error("Missing CUSTOM_VISION_MODEL_NAME")
    }
}