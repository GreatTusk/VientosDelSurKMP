package com.portafolio.vientosdelsur.domain.room.model

data class RoomState(
    val room: Room,
    val roomCleaningType: RoomCleaningType,
    val roomCleaningStatus: RoomCleaningStatus
)
