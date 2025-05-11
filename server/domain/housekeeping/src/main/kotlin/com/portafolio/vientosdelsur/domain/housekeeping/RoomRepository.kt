package com.portafolio.vientosdelsur.domain.housekeeping

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.shared.domain.Room
import com.portafolio.vientosdelsur.shared.domain.RoomState
import kotlinx.datetime.LocalDate

interface RoomRepository {
    suspend fun getAllRooms(): Result<List<Room>, DataError.Remote>

    suspend fun getRoomDistributionForHousekeeperOn(
        housekeeperId: Int,
        date: LocalDate
    ): Result<List<RoomState>, DataError.Remote>

    suspend fun getAllRoomStatusOn(date: LocalDate): Result<List<RoomState>, DataError.Remote>

}