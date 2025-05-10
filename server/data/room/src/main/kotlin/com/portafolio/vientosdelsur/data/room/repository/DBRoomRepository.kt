package com.portafolio.vientosdelsur.data.room.repository

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.core.database.entity.employee.EmployeeDao
import com.portafolio.vientosdelsur.core.database.entity.room.RoomDao
import com.portafolio.vientosdelsur.core.database.util.suspendTransaction
import com.portafolio.vientosdelsur.data.room.mapper.toRoomDto
import com.portafolio.vientosdelsur.shared.dto.RoomDto

object DBRoomRepository: RoomRepository {
    override suspend fun getAllRooms(): Result<List<RoomDto>, DataError.Remote> = suspendTransaction {
        return@suspendTransaction try {
            Result.Success(RoomDao.all().map { it.toRoomDto() })
        } catch (e: Exception) {
            Result.Error(DataError.Remote.UNKNOWN)
        }
    }
}