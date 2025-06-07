package com.portafolio.vientosdelsur.service.imageanalysis

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.microsoft.azure.cognitiveservices.vision.customvision.prediction.CustomVisionPredictionClient
import com.portafolio.vientosdelsur.service.imageanalysis.mapper.toImageAnalysisResult
import com.portafolio.vientosdelsur.service.imageanalysis.util.asFlow
import com.portafolio.vientosdelsur.shared.dto.imageanalysis.ImageAnalysisRequest
import com.portafolio.vientosdelsur.shared.dto.imageanalysis.ImageAnalysisResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.single
import java.util.*

internal class AzureImageAnalysisService(
    private val predictor: CustomVisionPredictionClient,
    private val ioDispatcher: CoroutineDispatcher
) : ImageAnalysisService {
    override suspend fun analyze(analysisRequest: ImageAnalysisRequest): Result<Set<ImageAnalysisResult>, DataError.Remote> =
        try {
            val results = predictor.predictions()
                .classifyImageAsync(
                    /* projectId = */ PROJECT_ID,
                    /* publishedName = */ MODEL_NAME,
                    /* imageData = */ analysisRequest.imageBytes,
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
        private val PROJECT_ID = UUID.fromString(System.getenv("CUSTOM_VISION_PROJECT_ID"))
        private val MODEL_NAME = System.getenv("CUSTOM_VISION_MODEL_NAME")
    }
}