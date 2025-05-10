package com.portafolio.vientosdelsur.data.room.mapper

import com.portafolio.vientosdelsur.core.database.entity.room.RoomDao
import com.portafolio.vientosdelsur.core.database.entity.room.RoomTypeDao
import com.portafolio.vientosdelsur.shared.dto.RoomDto
import com.portafolio.vientosdelsur.shared.dto.RoomTypeDto

internal fun RoomDao.toRoomDto() = RoomDto(
    id = id.value,
    roomNumber = roomNumber,
    roomType = roomType.toRoomTypeDto()
)


private fun RoomTypeDao.toRoomTypeDto() = RoomTypeDto(
    roomType = roomType.name,
    workUnit = workUnit,
    checkOutWorkUnit = checkOutWorkUnit
)