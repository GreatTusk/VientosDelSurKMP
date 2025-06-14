package com.portafolio.vientosdelsur.shared.dto.imageanalysis

import com.portafolio.vientosdelsur.shared.dto.room.RoomDto
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class ImageAnalysisDto(
    val id: Int,
    val room: RoomDto,
    val updatedAt: LocalDateTime,
    val imageAnalysisResultDto: ImageAnalysisResultDto,
    val imageUrl: String
)