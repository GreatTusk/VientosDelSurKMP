package com.portafolio.vientosdelsur.service.imageanalysis.mapper

import com.portafolio.vientosdelsur.domain.imageanalysis.storage.ImageAnalysis
import com.portafolio.vientosdelsur.service.housekeeping.mapper.toRoomDto
import com.portafolio.vientosdelsur.shared.dto.imageanalysis.ImageAnalysisDto

internal fun ImageAnalysis.toImageAnalysisDto() = ImageAnalysisDto(
    id = id,
    room = room.toRoomDto(),
    updatedAt = updatedAt,
    imageAnalysisResultDto = analysisConclusion.toImageAnalysisResultDto(),
    imageUrl = image
)