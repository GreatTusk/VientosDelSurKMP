package com.f776.japanesedictionary.data.imageanalysis.mapper

import com.f776.japanesedictionary.domain.imageanalysis.RoomAnalysis
import com.f776.japanesedictionary.domain.imageanalysis.RoomApprovalStatus
import com.portafolio.vientosdelsur.data.room.mapper.toRoom
import com.portafolio.vientosdelsur.network.BuildConfig
import com.portafolio.vientosdelsur.shared.dto.imageanalysis.ImageAnalysisDto

internal fun ImageAnalysisDto.toImageAnalysis() = RoomAnalysis(
    id = id,
    room = room.toRoom(),
    updatedAt = updatedAt,
    result = imageAnalysisResultDto.toImageAnalysisResult(),
    imageUrl = "${BuildConfig.BASE_URL}/$imageUrl",
    approvalStatus = RoomApprovalStatus.APPROVED
)