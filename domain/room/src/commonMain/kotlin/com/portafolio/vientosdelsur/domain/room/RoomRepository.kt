package com.portafolio.vientosdelsur.domain.room

import com.f776.core.common.DataError
import com.f776.core.common.Result

interface RoomRepository {
    suspend fun getAllRooms(): Result<List<Room>, DataError.Remote>
}