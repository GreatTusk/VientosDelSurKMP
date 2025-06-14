package com.portafolio.vientosdelsur.data.room.mapper

import com.portafolio.vientosdelsur.core.database.entity.room.RoomEntity
import com.portafolio.vientosdelsur.core.database.entity.room.RoomType
import com.portafolio.vientosdelsur.core.database.entity.room.RoomTypeEntity
import com.portafolio.vientosdelsur.domain.room.model.Room
import com.portafolio.vientosdelsur.domain.room.model.RoomTypeDetails

fun RoomEntity.toRoom() = Room(
    id = id.value,
    roomType = roomType.toRoomTypeDetails(),
    number = roomNumber.toInt(),
)

private fun RoomTypeEntity.toRoomTypeDetails() = RoomTypeDetails(
    roomType = when (roomType) {
        RoomType.SINGLE -> com.portafolio.vientosdelsur.domain.room.model.RoomType.SINGLE
        RoomType.DOUBLE -> com.portafolio.vientosdelsur.domain.room.model.RoomType.DOUBLE
        RoomType.TRIPLE -> com.portafolio.vientosdelsur.domain.room.model.RoomType.TRIPLE
        RoomType.QUAD -> com.portafolio.vientosdelsur.domain.room.model.RoomType.QUAD
    },
    workUnit = workUnit,
    checkOutWorkUnit = checkOutWorkUnit
)