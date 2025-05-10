package com.portafolio.vientosdelsur.data.room.repository

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.shared.dto.RoomDto

interface RoomRepository {
    suspend fun getAllRooms(): Result<List<RoomDto>, DataError.Remote>
}