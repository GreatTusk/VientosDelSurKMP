package com.portafolio.vientosdelsur.shared.dto.imageanalysis

import kotlinx.serialization.Serializable

@Serializable
data class RoomCleaningReviewDto(
    val roomAnalysisId: Int,
    val roomAnalysisStatusDto: RoomAnalysisStatusDto
)
