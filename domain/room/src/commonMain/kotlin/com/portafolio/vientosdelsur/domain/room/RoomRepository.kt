package com.portafolio.vientosdelsur.domain.room

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.shared.domain.Room

interface RoomRepository {
    suspend fun getAllRooms(): Result<List<Room>, DataError.Remote>
}