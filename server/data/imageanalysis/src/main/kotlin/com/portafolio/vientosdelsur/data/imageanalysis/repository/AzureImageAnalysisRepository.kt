package com.portafolio.vientosdelsur.data.imageanalysis.repository

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.f776.core.common.map
import com.f776.core.network.safeCall
import com.portafolio.vientosdelsur.data.imageanalysis.dto.PredictionResponse
import com.portafolio.vientosdelsur.data.imageanalysis.mapper.toImageAnalysisResult
import com.portafolio.vientosdelsur.domain.imageanalysis.ImageAnalysisRepository
import com.portafolio.vientosdelsur.domain.imageanalysis.ImageAnalysisResult
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import java.util.*

internal class AzureImageAnalysisRepository(private val httpClient: HttpClient) : ImageAnalysisRepository {
    override suspend fun analyze(imageBytes: ByteArray): Result<List<ImageAnalysisResult>, DataError.Remote> = safeCall<PredictionResponse> {
        httpClient.post("$PREDICTION_ENDPOINT/customvision/v3.0/Prediction/$PROJECT_ID/classify/iterations/$PUBLISHED_NAME/image") {
            setBody(imageBytes)
            headers {
                append("Content-Type", "application/octet-stream")
                append("Prediction-Key", PREDICTION_API_KEY)
            }
        }.body()
    }.map { predictionResponse -> predictionResponse.predictions.map { it.toImageAnalysisResult() } }

    companion object {
        private val PROJECT_ID =
            UUID.fromString(System.getenv("CUSTOM_VISION_PROJECT_ID")) ?: error("Missing CUSTOM_VISION_PROJECT_ID")
        private val PUBLISHED_NAME =
            System.getenv("CUSTOM_VISION_PUBLISHED_NAME") ?: error("Missing CUSTOM_VISION_PUBLISHED_NAME")
        private val PREDICTION_API_KEY =
            System.getenv("CUSTOM_VISION_PREDICTION_KEY") ?: error("Missing CUSTOM_VISION_PREDICTION_KEY")
        private val PREDICTION_ENDPOINT =
            System.getenv("CUSTOM_VISION_PREDICTION_ENDPOINT") ?: error("Missing CUSTOM_VISION_PREDICTION_ENDPOINT")
    }
}