package com.portafolio.vientosdelsur.data.imageanalysis.mapper

import com.portafolio.vientosdelsur.data.imageanalysis.dto.PredictionDto
import com.portafolio.vientosdelsur.domain.imageanalysis.ImageAnalysisResult
import com.portafolio.vientosdelsur.domain.imageanalysis.ResultTag

fun PredictionDto.toImageAnalysisResult() = ImageAnalysisResult(
    tagName = fromTagName(tagName),
    probability = probability.toFloat()
)

fun fromTagName(tagName: String) =
    when (tagName) {
        "Desordenada" -> ResultTag.UNCLEAN
        "Ordenada" -> ResultTag.CLEAN
        else -> error("Unknown tag")
    }
