package com.portafolio.vientosdelsur.shared.domain

data class RoomState(
    val room: Room,
    val roomCleaningType: RoomCleaningType,
    val roomCleaningStatus: RoomCleaningStatus
)
