package com.portafolio.vientosdelsur.service.imageanalysis.mapper

import com.portafolio.vientosdelsur.domain.imageanalysis.RoomAnalysisState
import com.portafolio.vientosdelsur.domain.imageanalysis.storage.ImageAnalysis
import com.portafolio.vientosdelsur.service.employee.mapper.toEmployeeDto
import com.portafolio.vientosdelsur.service.housekeeping.mapper.toRoomDto
import com.portafolio.vientosdelsur.shared.dto.employee.EmployeeDto
import com.portafolio.vientosdelsur.shared.dto.imageanalysis.ImageAnalysisDto
import com.portafolio.vientosdelsur.shared.dto.imageanalysis.RoomAnalysisStatusDto

internal fun ImageAnalysis.toImageAnalysisDto() = ImageAnalysisDto(
    id = id,
    room = room.toRoomDto(),
    updatedAt = updatedAt,
    imageAnalysisResultDto = analysisConclusion.toImageAnalysisResultDto(),
    imageUrl = imageUrl,
    uploadedBy = requireNotNull(uploadedBy.toEmployeeDto() as EmployeeDto.Get.Housekeeper) { "Uploader was not housekeeper" },
    roomAnalysisStatusDto = roomAnalysisState.toRoomAnalysisStateDto()
)

private fun RoomAnalysisState.toRoomAnalysisStateDto() = when (this) {
    RoomAnalysisState.APPROVED -> RoomAnalysisStatusDto.APPROVED
    RoomAnalysisState.REJECTED -> RoomAnalysisStatusDto.REJECTED
    RoomAnalysisState.PENDING -> RoomAnalysisStatusDto.PENDING
}

internal fun RoomAnalysisStatusDto.toRoomAnalysisState() = when (this) {
    RoomAnalysisStatusDto.APPROVED -> RoomAnalysisState.APPROVED
    RoomAnalysisStatusDto.REJECTED -> RoomAnalysisState.REJECTED
    RoomAnalysisStatusDto.PENDING -> RoomAnalysisState.PENDING
}