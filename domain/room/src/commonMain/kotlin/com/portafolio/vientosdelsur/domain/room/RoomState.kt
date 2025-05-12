package com.portafolio.vientosdelsur.domain.room

import kotlinx.datetime.LocalDateTime

data class RoomState(
    val room: Room,
    val cleaningType: RoomCleaningType,
    val cleaningStatus: RoomCleaningStatus
)

sealed interface RoomCleaningStatus {
    data object Pending : RoomCleaningStatus
    data class InCleaning(val changedAt: LocalDateTime) : RoomCleaningStatus
    data class InRevision(val changedAt: LocalDateTime) : RoomCleaningStatus
    data class Done(val changedAt: LocalDateTime) : RoomCleaningStatus
}

enum class RoomCleaningType {
    ROOM,
    GUEST
}