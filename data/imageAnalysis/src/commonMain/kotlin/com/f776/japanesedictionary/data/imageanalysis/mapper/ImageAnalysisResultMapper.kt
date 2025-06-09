package com.f776.japanesedictionary.data.imageanalysis.mapper

import com.f776.japanesedictionary.domain.imageanalysis.ImageAnalysisResult
import com.portafolio.vientosdelsur.shared.dto.imageanalysis.ImageAnalysisResultDto

internal fun ImageAnalysisResultDto.toImageAnalysisResult() = when (this) {
    ImageAnalysisResultDto.CLEAN -> ImageAnalysisResult.CLEAN
    ImageAnalysisResultDto.SLIGHTLY_DIRTY -> ImageAnalysisResult.SLIGHTLY_DIRTY
    ImageAnalysisResultDto.MODERATELY_DIRTY -> ImageAnalysisResult.MODERATELY_DIRTY
    ImageAnalysisResultDto.VERY_DIRTY -> ImageAnalysisResult.VERY_DIRTY
    ImageAnalysisResultDto.EXTREMELY_DIRTY -> ImageAnalysisResult.EXTREMELY_DIRTY
}