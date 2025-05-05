package com.portafolio.vientosdelsur.domain.housekeeping

data class Housekeeper(
    val id: Int,
    val shiftMinutes: Int,
    val preferredFloor: Floor?
)
