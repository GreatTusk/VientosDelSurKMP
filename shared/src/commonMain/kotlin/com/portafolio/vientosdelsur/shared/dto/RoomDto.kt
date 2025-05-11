package com.portafolio.vientosdelsur.shared.dto

import com.portafolio.vientosdelsur.shared.domain.RoomCleaningType
import com.portafolio.vientosdelsur.shared.domain.RoomType
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class RoomDto(
    val id: Int,
    val roomNumber: String,
    val roomType: RoomType
)

@Serializable
data class RoomStateDto(
    val room: RoomDto,
    val cleaningType: RoomCleaningType,
    val currentStatus: RoomCleaningStatusDto
)

@Serializable
data class RoomCleaningStatusDto(
    val status: RoomStatusDto,
    val updatedAt: LocalDateTime?
)

@Serializable
enum class RoomStatusDto {
    PENDING, IN_PROGRESS, IN_REVISION, DONE
}