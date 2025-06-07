package com.portafolio.vientosdelsur.shared.dto.imageanalysis

import kotlinx.serialization.Serializable

@Serializable
data class ImageAnalysisResult(
    val tagName: ResultTagDto,
    val probability: Float
)