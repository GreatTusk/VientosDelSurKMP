package com.portafolio.vientosdelsur.data.room.mapper

import com.portafolio.vientosdelsur.core.database.entity.room.RoomEntity
import com.portafolio.vientosdelsur.core.database.entity.room.RoomTypeEntity
import com.portafolio.vientosdelsur.shared.domain.Room
import com.portafolio.vientosdelsur.shared.domain.RoomTypeDetails

internal fun RoomEntity.toRoom() = Room(
    id = id.value,
    roomType = roomType.toRoomTypeDetails() ,
    number = roomNumber.toInt(),
)

private fun RoomTypeEntity.toRoomTypeDetails() = RoomTypeDetails(
    roomType = roomType,
    workUnit = workUnit,
    checkOutWorkUnit = checkOutWorkUnit
)