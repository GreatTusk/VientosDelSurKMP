package com.portafolio.vientosdelsur.data.imageanalysis.mapper

import com.portafolio.vientosdelsur.core.database.entity.imageanalysis.RoomAnalysisTable
import com.portafolio.vientosdelsur.domain.imageanalysis.RoomAnalysisState

internal fun RoomAnalysisState.toRoomAnalysisEntity() = when (this) {
    RoomAnalysisState.APPROVED -> RoomAnalysisTable.APPROVED
    RoomAnalysisState.REJECTED -> RoomAnalysisTable.REJECTED
    RoomAnalysisState.PENDING -> RoomAnalysisTable.PENDING
}