package com.portafolio.vientosdelsur.domain.room

import com.f776.core.common.DataError
import com.f776.core.common.Result
import kotlinx.datetime.LocalDate

interface RoomRepository {
    suspend fun getAllRooms(): Result<List<Room>, DataError.Remote>
    suspend fun getAllRoomsState(date: LocalDate): Result<List<RoomState>, DataError.Remote>
    suspend fun getRoomDistributionForHousekeeperOn(
        housekeeperId: Int,
        date: LocalDate
    ): Result<List<RoomState>, DataError.Remote>
}