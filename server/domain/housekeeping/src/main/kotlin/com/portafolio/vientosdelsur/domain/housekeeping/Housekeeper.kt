package com.portafolio.vientosdelsur.domain.housekeeping

import com.portafolio.vientosdelsur.shared.domain.Floor

data class Housekeeper(
    val id: Int,
    val shiftMinutes: Int,
    val preferredFloor: Floor?
)
