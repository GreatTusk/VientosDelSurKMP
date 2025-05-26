package com.portafolio.vientosdelsur.data.room.repository

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.f776.core.common.flatMap
import com.portafolio.vientosdelsur.data.room.mapper.toRoom
import com.portafolio.vientosdelsur.data.room.mapper.toRoomState
import com.portafolio.vientosdelsur.data.room.network.RemoteRoomDataSource
import com.portafolio.vientosdelsur.domain.room.Room
import com.portafolio.vientosdelsur.domain.room.RoomRepository
import com.portafolio.vientosdelsur.domain.room.RoomState
import kotlinx.datetime.LocalDate

internal class RoomRepositoryImpl(private val remoteRoomDatasource: RemoteRoomDataSource) : RoomRepository {
    override suspend fun getAllRooms(): Result<List<Room>, DataError.Remote> {
        return remoteRoomDatasource.getAllRooms().flatMap { it.toRoom() }
    }

    override suspend fun getAllRoomsState(date: LocalDate): Result<List<RoomState>, DataError.Remote> {
        return remoteRoomDatasource.getAllRoomsState(date)
            .flatMap { it.toRoomState() }
    }

    override suspend fun getRoomDistributionForHousekeeperOn(
        housekeeperId: Int,
        date: LocalDate
    ): Result<List<RoomState>, DataError.Remote> {
        return remoteRoomDatasource.getRoomDistributionForHousekeeperOn(housekeeperId, date)
            .flatMap { it.toRoomState() }
    }
}