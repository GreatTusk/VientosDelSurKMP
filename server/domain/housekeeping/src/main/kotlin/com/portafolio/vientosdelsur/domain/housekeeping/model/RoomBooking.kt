package com.portafolio.vientosdelsur.domain.housekeeping.model

data class RoomBooking(
    val room: Room,
    val workUnits: Int
)

data class RoomBookingId(
    val roomId: Int,
    val workUnits: Int
)