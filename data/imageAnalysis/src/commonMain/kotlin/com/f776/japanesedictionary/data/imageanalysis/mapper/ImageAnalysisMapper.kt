package com.f776.japanesedictionary.data.imageanalysis.mapper

import com.f776.japanesedictionary.domain.imageanalysis.RoomAnalysis
import com.f776.japanesedictionary.domain.imageanalysis.RoomApprovalStatus
import com.portafolio.vientosdelsur.data.employee.mapper.toEmployee
import com.portafolio.vientosdelsur.data.room.mapper.toRoom
import com.portafolio.vientosdelsur.network.BuildConfig
import com.portafolio.vientosdelsur.shared.dto.imageanalysis.ImageAnalysisDto
import com.portafolio.vientosdelsur.shared.dto.imageanalysis.RoomAnalysisStatusDto

internal fun ImageAnalysisDto.toImageAnalysis() = RoomAnalysis(
    id = id,
    room = room.toRoom(),
    updatedAt = updatedAt,
    result = imageAnalysisResultDto.toImageAnalysisResult(),
    imageUrl = "${BuildConfig.BASE_URL}/$imageUrl",
    approvalStatus = roomAnalysisStatusDto.toRoomApprovalStatus(),
    housekeeper = uploadedBy.toEmployee()
)

internal fun RoomAnalysisStatusDto.toRoomApprovalStatus() = when (this) {
    RoomAnalysisStatusDto.APPROVED -> RoomApprovalStatus.APPROVED
    RoomAnalysisStatusDto.REJECTED -> RoomApprovalStatus.REJECTED
    RoomAnalysisStatusDto.PENDING -> RoomApprovalStatus.PENDING
}