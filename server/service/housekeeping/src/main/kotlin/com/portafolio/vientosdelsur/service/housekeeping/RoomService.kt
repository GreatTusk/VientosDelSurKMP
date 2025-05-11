package com.portafolio.vientosdelsur.service.housekeeping

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.shared.dto.BaseResponseDto
import com.portafolio.vientosdelsur.shared.dto.RoomDto
import com.portafolio.vientosdelsur.shared.dto.RoomStateDto
import kotlinx.datetime.LocalDate

typealias RoomResponse = BaseResponseDto<List<RoomDto>>
typealias RoomStateResponse = BaseResponseDto<List<RoomStateDto>>

interface RoomService {
    suspend fun getAllRooms(): Result<RoomResponse, DataError.Remote>

    suspend fun getRoomDistributionForHousekeeperOn(
        housekeeperId: Int,
        date: LocalDate
    ): Result<RoomStateResponse, DataError.Remote>

    suspend fun getAllRoomStatusOn(date: LocalDate): Result<RoomStateResponse, DataError.Remote>
}