package com.portafolio.vientosdelsur.data.room.repository

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.data.room.network.RemoteRoomDatasource
import com.portafolio.vientosdelsur.domain.room.Room
import com.portafolio.vientosdelsur.domain.room.RoomRepository

internal class RoomRepositoryImpl(private val remoteRoomDatasource: RemoteRoomDatasource): RoomRepository {
    override suspend fun getAllRooms(): Result<List<Room>, DataError.Remote> {
        return remoteRoomDatasource.getAllRooms()
    }
}