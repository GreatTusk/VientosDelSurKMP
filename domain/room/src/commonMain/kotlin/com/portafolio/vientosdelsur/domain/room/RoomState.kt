package com.portafolio.vientosdelsur.domain.room

data class RoomState(
    val room: Room,
    val roomCleaningType: RoomCleaningType,
    val roomCleaningStatus: RoomCleaningStatus
)
