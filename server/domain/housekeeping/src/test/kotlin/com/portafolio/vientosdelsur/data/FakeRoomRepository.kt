package com.portafolio.vientosdelsur.data

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.domain.room.RoomRepository
import com.portafolio.vientosdelsur.domain.room.model.Room
import com.portafolio.vientosdelsur.domain.room.model.RoomState
import com.portafolio.vientosdelsur.domain.room.model.RoomType
import com.portafolio.vientosdelsur.domain.room.model.RoomTypeDetails
import kotlinx.datetime.LocalDate

class FakeRoomRepository: RoomRepository {

    private val roomTypeDetailsMap = mapOf(
        RoomType.SINGLE to RoomTypeDetails(RoomType.SINGLE, workUnit = 18, checkOutWorkUnit = 25),
        RoomType.DOUBLE to RoomTypeDetails(RoomType.DOUBLE, workUnit = 28, checkOutWorkUnit = 40),
        RoomType.TRIPLE to RoomTypeDetails(RoomType.TRIPLE, workUnit = 42, checkOutWorkUnit = 60),
        RoomType.QUAD to RoomTypeDetails(RoomType.QUAD, workUnit = 56, checkOutWorkUnit = 80)
    )

    private val roomData = listOf(
        // First floor
        101 to RoomType.TRIPLE,
        102 to RoomType.DOUBLE,
        104 to RoomType.SINGLE,
        105 to RoomType.DOUBLE,

        // Second floor
        206 to RoomType.DOUBLE,
        207 to RoomType.DOUBLE,
        208 to RoomType.DOUBLE,
        209 to RoomType.DOUBLE,
        210 to RoomType.DOUBLE,
        211 to RoomType.DOUBLE,
        212 to RoomType.DOUBLE,
        213 to RoomType.DOUBLE,
        214 to RoomType.DOUBLE,
        215 to RoomType.DOUBLE,
        216 to RoomType.DOUBLE,

        // Third floor
        317 to RoomType.TRIPLE,
        318 to RoomType.QUAD,
        319 to RoomType.DOUBLE,
        320 to RoomType.DOUBLE,
        321 to RoomType.TRIPLE,
        322 to RoomType.QUAD,
        323 to RoomType.DOUBLE,

        // Fourth floor
        424 to RoomType.QUAD,
        425 to RoomType.QUAD,
        426 to RoomType.DOUBLE,
        427 to RoomType.DOUBLE,
        428 to RoomType.DOUBLE,
        429 to RoomType.DOUBLE
    )

    override suspend fun getAllRooms(): Result<List<Room>, DataError.Remote> {
        val rooms: List<Room> = roomData.mapIndexed { index, (number, type) ->
            Room(
                id = index + 1,
                number = number,
                roomType = roomTypeDetailsMap[type] ?: error("Missing RoomTypeDetails for $type")
            )
        }

        return Result.Success(rooms)
    }

    override suspend fun getRoomDistributionForHousekeeperOn(
        housekeeperId: Int,
        date: LocalDate
    ): Result<List<RoomState>, DataError.Remote> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllRoomStatusOn(date: LocalDate): Result<List<RoomState>, DataError.Remote> {
        TODO("Not yet implemented")
    }
}