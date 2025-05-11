package com.portafolio.vientosdelsur.data.room.mapper

import com.portafolio.vientosdelsur.core.database.entity.room.RoomEntity
import com.portafolio.vientosdelsur.core.database.entity.room.RoomTypeEntity
import com.portafolio.vientosdelsur.shared.dto.RoomDto
import com.portafolio.vientosdelsur.shared.dto.RoomTypeDto

internal fun RoomEntity.toRoomDto() = RoomDto(
    id = id.value,
    roomNumber = roomNumber,
    roomType = roomType.toRoomTypeDto()
)


private fun RoomTypeEntity.toRoomTypeDto() = RoomTypeDto(
    roomType = roomType.name,
    workUnit = workUnit,
    checkOutWorkUnit = checkOutWorkUnit
)