package com.portafolio.vientosdelsur.domain.room.model

import kotlinx.datetime.LocalDateTime

sealed interface RoomCleaningStatus {
    data object Pending : RoomCleaningStatus
    data class InCleaning(val changedAt: LocalDateTime) : RoomCleaningStatus
    data class InRevision(val changedAt: LocalDateTime) : RoomCleaningStatus
    data class Done(val changedAt: LocalDateTime) : RoomCleaningStatus
}