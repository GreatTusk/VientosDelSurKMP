package com.portafolio.vientosdelsur.data.imageanalysis.di

import com.microsoft.azure.cognitiveservices.vision.customvision.prediction.CustomVisionPredictionClient
import com.microsoft.azure.cognitiveservices.vision.customvision.prediction.CustomVisionPredictionManager

internal object CustomVisionPredictorFactory {
    private val PREDICTION_API_KEY =
        System.getenv("CUSTOM_VISION_PREDICTION_KEY") ?: error("Missing CUSTOM_VISION_PREDICTION_KEY")
    private val PREDICTION_ENDPOINT =
        System.getenv("CUSTOM_VISION_PREDICTION_ENDPOINT") ?: error("Missing CUSTOM_VISION_PREDICTION_ENDPOINT")

    fun create(): CustomVisionPredictionClient =
        CustomVisionPredictionManager
            .authenticate(PREDICTION_ENDPOINT, PREDICTION_API_KEY)
            .withEndpoint(PREDICTION_ENDPOINT)
}