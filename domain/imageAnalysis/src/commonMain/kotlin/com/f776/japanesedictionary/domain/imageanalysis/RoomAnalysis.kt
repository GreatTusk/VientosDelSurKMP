package com.f776.japanesedictionary.domain.imageanalysis

import com.portafolio.vientosdelsur.domain.room.Room
import kotlinx.datetime.LocalDateTime

data class RoomAnalysis(
    val id: Int,
    val room: Room,
    val updatedAt: LocalDateTime,
    val result: ImageAnalysisResult,
    val imageUrl: String,
    val approvalStatus: RoomApprovalStatus
)

enum class RoomApprovalStatus { APPROVED, PENDING, REJECTED }