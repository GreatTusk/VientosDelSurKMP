package com.portafolio.vientosdelsur.service.housekeeping

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.f776.core.common.flatMap
import com.f776.core.common.map
import com.portafolio.vientosdelsur.domain.housekeeping.RoomRepository
import com.portafolio.vientosdelsur.service.housekeeping.mapper.toRoomDto
import com.portafolio.vientosdelsur.service.housekeeping.mapper.toRoomStateDto
import com.portafolio.vientosdelsur.shared.dto.BaseResponseDto
import kotlinx.datetime.LocalDate

internal class RoomServiceImpl(private val roomRepository: RoomRepository) : RoomService {
    override suspend fun getAllRooms(): Result<RoomResponse, DataError.Remote> {
        return roomRepository.getAllRooms()
            .flatMap { it.toRoomDto() }
            .map { rooms ->
                BaseResponseDto(
                    message = "Retrieved rooms successfully",
                    data = rooms
                )
            }
    }

    override suspend fun getRoomDistributionForHousekeeperOn(
        housekeeperId: Int,
        date: LocalDate
    ): Result<RoomStateResponse, DataError.Remote> {
        return roomRepository.getRoomDistributionForHousekeeperOn(housekeeperId, date)
            .flatMap { it.toRoomStateDto() }
            .map { rooms ->
                BaseResponseDto(
                    message = "Retrieved rooms successfully",
                    data = rooms
                )
            }
    }

    override suspend fun getAllRoomStatusOn(date: LocalDate): Result<RoomStateResponse, DataError.Remote> {
        return roomRepository.getAllRoomStatusOn(date)
            .flatMap { it.toRoomStateDto() }
            .map { rooms ->
                BaseResponseDto(
                    message = "Retrieved rooms successfully",
                    data = rooms
                )
            }
    }
}