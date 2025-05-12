package com.portafolio.vientosdelsur.data.room.mapper

import com.portafolio.vientosdelsur.domain.room.Room
import com.portafolio.vientosdelsur.domain.room.RoomType
import com.portafolio.vientosdelsur.shared.dto.RoomDto

fun RoomDto.toRoom(): Room {
    return Room(
        id = id,
        roomType = RoomType.valueOf(roomType),
        roomNumber = roomNumber
    )
}