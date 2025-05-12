package com.portafolio.vientosdelsur.domain.housekeeping

import com.portafolio.vientosdelsur.domain.housekeeping.model.Floor

data class Housekeeper(
    val id: Int,
    val shiftMinutes: Int,
    val preferredFloor: Floor?
)
