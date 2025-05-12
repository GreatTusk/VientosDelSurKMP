package com.portafolio.vientosdelsur.shared.dto

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class RoomDto(
    val id: Int,
    val roomNumber: String,
    val roomType: String
)

@Serializable
data class RoomStateDto(
    val room: RoomDto,
    val cleaningType: String,
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