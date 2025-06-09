package com.portafolio.vientosdelsur.service.imageanalysis.mapper

import com.portafolio.vientosdelsur.domain.imageanalysis.AnalysisConclusion
import com.portafolio.vientosdelsur.shared.dto.imageanalysis.ImageAnalysisResultDto

internal fun AnalysisConclusion.toImageAnalysisResultDto() = when (this) {
    AnalysisConclusion.CLEAN -> ImageAnalysisResultDto.CLEAN
    AnalysisConclusion.SLIGHTLY_DIRTY -> ImageAnalysisResultDto.SLIGHTLY_DIRTY
    AnalysisConclusion.MODERATELY_DIRTY -> ImageAnalysisResultDto.MODERATELY_DIRTY
    AnalysisConclusion.VERY_DIRTY -> ImageAnalysisResultDto.VERY_DIRTY
    AnalysisConclusion.EXTREMELY_DIRTY -> ImageAnalysisResultDto.EXTREMELY_DIRTY
}