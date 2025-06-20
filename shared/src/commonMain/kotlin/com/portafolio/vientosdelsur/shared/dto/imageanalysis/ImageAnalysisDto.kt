package com.portafolio.vientosdelsur.shared.dto.imageanalysis

import com.portafolio.vientosdelsur.shared.dto.employee.EmployeeDto
import com.portafolio.vientosdelsur.shared.dto.room.RoomDto
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class ImageAnalysisDto(
    val id: Int,
    val room: RoomDto,
    val updatedAt: LocalDateTime,
    val imageAnalysisResultDto: ImageAnalysisResultDto,
    val imageUrl: String,
    val uploadedBy: EmployeeDto.Get.Housekeeper,
    val roomAnalysisStatusDto: RoomAnalysisStatusDto
)

enum class RoomAnalysisStatusDto { APPROVED, REJECTED, PENDING }