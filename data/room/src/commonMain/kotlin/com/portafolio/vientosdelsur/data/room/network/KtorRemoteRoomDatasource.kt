package com.portafolio.vientosdelsur.data.room.network

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.f776.core.common.flatMap
import com.f776.core.network.safeCall
import com.portafolio.vientosdelsur.data.room.mapper.toRoom
import com.portafolio.vientosdelsur.domain.room.Room
import com.portafolio.vientosdelsur.shared.dto.RoomDto
import io.ktor.client.*
import io.ktor.client.request.*

internal class KtorRemoteRoomDatasource(private val httpClient: HttpClient): RemoteRoomDatasource {
    override suspend fun getAllRooms(): Result<List<Room>, DataError.Remote> = safeCall<List<RoomDto>> {
        httpClient.get("http://localhost:8080/room")
    }.flatMap { it.toRoom() }
}