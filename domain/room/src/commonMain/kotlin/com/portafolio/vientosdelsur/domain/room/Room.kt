package com.portafolio.vientosdelsur.domain.room

import com.portafolio.vientosdelsur.shared.domain.RoomType

data class Room(
    val id: Int,
    val roomNumber: String,
    val roomType: RoomType
)