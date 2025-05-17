package com.portafolio.vientosdelsur.domain.housekeeping.model

import kotlinx.datetime.LocalDate

data class RoomBooking(
    val room: Room,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val workUnits: Int
)
