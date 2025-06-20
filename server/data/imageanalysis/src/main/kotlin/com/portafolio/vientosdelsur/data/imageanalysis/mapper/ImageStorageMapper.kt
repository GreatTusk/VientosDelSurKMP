package com.portafolio.vientosdelsur.data.imageanalysis.mapper

import com.portafolio.vientosdelsur.core.database.entity.imageanalysis.ImageAnalysisEntity
import com.portafolio.vientosdelsur.core.database.entity.imageanalysis.RoomAnalysisTable
import com.portafolio.vientosdelsur.data.employee.mapper.toEmployee
import com.portafolio.vientosdelsur.data.room.mapper.toRoom
import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.domain.imageanalysis.RoomAnalysisState
import com.portafolio.vientosdelsur.domain.imageanalysis.storage.ImageAnalysis

internal fun ImageAnalysisEntity.toImageAnalysis() = ImageAnalysis(
    id = id.value,
    room = room.toRoom(),
    updatedAt = uploadedAt,
    cleanProbability = cleanProbability,
    uncleanProbability = uncleanProbability,
    imageUrl = "/image-analysis/image/${id.value}",
    uploadedBy = requireNotNull(housekeeper.toEmployee() as Employee.Housekeeper) { "Uploader is not a housekeeper" },
    roomAnalysisState = roomAnalysisStatus.toRoomAnalysisState()
)

internal fun RoomAnalysisTable.toRoomAnalysisState() = when (this) {
    RoomAnalysisTable.APPROVED -> RoomAnalysisState.APPROVED
    RoomAnalysisTable.REJECTED -> RoomAnalysisState.REJECTED
    RoomAnalysisTable.PENDING -> RoomAnalysisState.PENDING
}
