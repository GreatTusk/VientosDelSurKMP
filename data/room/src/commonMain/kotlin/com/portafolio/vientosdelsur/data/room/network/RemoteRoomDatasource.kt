package com.portafolio.vientosdelsur.data.room.network

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.domain.room.Room

internal interface RemoteRoomDatasource {
    suspend fun getAllRooms(): Result<List<Room>, DataError.Remote>
//    suspend fun getAllRoomsState(): Result<List<RoomState>, DataError.Remote>
}