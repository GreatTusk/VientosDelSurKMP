package com.portafolio.vientosdelsur.domain.imageanalysis

data class ImageAnalysisResult(
    val tagName: ResultTag,
    val probability: Double
)