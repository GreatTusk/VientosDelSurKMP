package com.portafolio.vientosdelsur.domain.room

import kotlinx.datetime.LocalDateTime

sealed interface RoomCleaningStatus {
    data object Pending : RoomCleaningStatus {
        override fun toString() = "Pendiente"
    }
    data class InCleaning(val changedAt: LocalDateTime) : RoomCleaningStatus {
        override fun toString() = "En progreso"
    }
    data class InRevision(val changedAt: LocalDateTime) : RoomCleaningStatus {
        override fun toString() = "En revisión"
    }
    data class Done(val changedAt: LocalDateTime) : RoomCleaningStatus {
        override fun toString() = "Finalizado"
    }
}