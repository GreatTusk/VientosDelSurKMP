package com.portafolio.vientosdelsur.shared.dto.imageanalysis

import kotlinx.serialization.Serializable

@Serializable
enum class ImageAnalysisResultDto {
    CLEAN,
    SLIGHTLY_DIRTY,
    MODERATELY_DIRTY,
    VERY_DIRTY,
    EXTREMELY_DIRTY
}