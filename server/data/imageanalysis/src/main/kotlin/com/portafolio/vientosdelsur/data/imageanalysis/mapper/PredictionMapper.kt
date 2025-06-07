package com.portafolio.vientosdelsur.data.imageanalysis.mapper

import com.microsoft.azure.cognitiveservices.vision.customvision.prediction.models.Prediction
import com.portafolio.vientosdelsur.domain.imageanalysis.ImageAnalysisResult
import com.portafolio.vientosdelsur.domain.imageanalysis.ResultTag

fun Prediction.toImageAnalysisResult() = ImageAnalysisResult(
    tagName = fromTagName(tagName()),
    probability = probability().toFloat()
)

fun fromTagName(tagName: String) =
    when (tagName) {
        "Desordenada" -> ResultTag.UNCLEAN
        "Ordenada" -> ResultTag.CLEAN
        else -> error("Unknown tag")
    }
