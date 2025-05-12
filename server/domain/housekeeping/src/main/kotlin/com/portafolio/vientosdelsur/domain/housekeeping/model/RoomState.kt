package com.portafolio.vientosdelsur.domain.housekeeping.model

data class RoomState(
    val room: Room,
    val roomCleaningType: RoomCleaningType,
    val roomCleaningStatus: RoomCleaningStatus
)
