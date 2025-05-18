package com.portafolio.vientosdelsur.domain.room.model

data class RoomBooking(
    val room: Room,
    val workUnits: Int,
    val cleaningType: RoomCleaningType
)

data class RoomBookingId(
    val roomId: Int,
    val workUnits: Int,
    val cleaningType: RoomCleaningType
)