package com.portafolio.vientosdelsur.service.imageanalysis.mapper

import com.microsoft.azure.cognitiveservices.vision.customvision.prediction.models.Prediction
import com.portafolio.vientosdelsur.shared.dto.imageanalysis.ImageAnalysisResult
import com.portafolio.vientosdelsur.shared.dto.imageanalysis.ResultTagDto

fun Prediction.toImageAnalysisResult() = ImageAnalysisResult(
    tagName = fromTagName(tagName()),
    probability = probability().toFloat()
)

fun fromTagName(tagName: String) =
    when (tagName) {
        "Desordenada" -> ResultTagDto.UNCLEAN
        "Ordenada" -> ResultTagDto.CLEAN
        else -> error("Unknown tag")
    }
