package com.portafolio.vientosdelsur.data.room.network

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.shared.dto.room.RoomDto
import com.portafolio.vientosdelsur.shared.dto.room.RoomStateDto
import kotlinx.datetime.LocalDate

internal interface RemoteRoomDataSource {
    suspend fun getAllRooms(): Result<List<RoomDto>, DataError.Remote>
    suspend fun getAllRoomsState(date: LocalDate): Result<List<RoomStateDto>, DataError.Remote>
    suspend fun getRoomDistributionForHousekeeperOn(
        housekeeperId: Int,
        date: LocalDate
    ): Result<List<RoomStateDto>, DataError.Remote>
}