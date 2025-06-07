package com.portafolio.vientosdelsur.service.imageanalysis.mapper

import com.portafolio.vientosdelsur.domain.imageanalysis.ImageAnalysisResult
import com.portafolio.vientosdelsur.domain.imageanalysis.ResultTag
import com.portafolio.vientosdelsur.shared.dto.imageanalysis.ImageAnalysisResultDto
import com.portafolio.vientosdelsur.shared.dto.imageanalysis.ResultTagDto

internal fun ImageAnalysisResult.toImageAnalysisResultDto() = ImageAnalysisResultDto(
    tagName = tagName.toResultTagDto(),
    probability = probability
)

private fun ResultTag.toResultTagDto() = when (this) {
    ResultTag.CLEAN -> ResultTagDto.CLEAN
    ResultTag.UNCLEAN -> ResultTagDto.UNCLEAN
}