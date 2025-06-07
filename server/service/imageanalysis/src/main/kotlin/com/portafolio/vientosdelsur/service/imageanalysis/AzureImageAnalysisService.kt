package com.portafolio.vientosdelsur.service.imageanalysis

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.microsoft.azure.cognitiveservices.vision.customvision.prediction.CustomVisionPredictionClient
import com.portafolio.vientosdelsur.shared.dto.imageanalysis.ImageAnalysisRequest
import com.portafolio.vientosdelsur.shared.dto.imageanalysis.ImageAnalysisResult
import java.util.*

internal class AzureImageAnalysisService(private val predictor: CustomVisionPredictionClient) : ImageAnalysisService {
    override suspend fun analyze(analysisRequest: ImageAnalysisRequest): Result<ImageAnalysisResult, DataError.Remote> {
        val results = predictor.predictions()
            .classifyImageAsync(
                /* projectId = */ PROJECT_ID,
                /* publishedName = */ MODEL_NAME,
                /* imageData = */ analysisRequest.imageBytes,
                /* classifyImageOptionalParameter = */ null
            )


        TODO("Not yet implemented")
    }

    companion object {
        private val PROJECT_ID = UUID.fromString(System.getenv("CUSTOM_VISION_PROJECT_ID"))
        private val MODEL_NAME = System.getenv("CUSTOM_VISION_MODEL_NAME")
    }
}