package com.portafolio.vientosdelsur.data.room.network

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.f776.core.common.map
import com.f776.core.network.safeCall
import com.portafolio.vientosdelsur.shared.dto.BaseResponseDto
import com.portafolio.vientosdelsur.shared.dto.RoomDto
import com.portafolio.vientosdelsur.shared.dto.RoomStateDto
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.datetime.LocalDate

internal class KtorRemoteRoomDatasource(private val httpClient: HttpClient) : RemoteRoomDatasource {
    override suspend fun getAllRooms(): Result<List<RoomDto>, DataError.Remote> =
        safeCall<BaseResponseDto<List<RoomDto>>> {
            httpClient.get("http://localhost:8080/room")
        }.map { it.data }


    override suspend fun getAllRoomsState(date: LocalDate): Result<List<RoomStateDto>, DataError.Remote> =
        safeCall<BaseResponseDto<List<RoomStateDto>>> {
            httpClient.get("http://localhost:8080/room/distribution") {
                parameter("date", date)
            }
        }.map { it.data }

    override suspend fun getRoomDistributionForHousekeeperOn(
        housekeeperId: Int,
        date: LocalDate
    ): Result<List<RoomStateDto>, DataError.Remote> =
        safeCall<BaseResponseDto<List<RoomStateDto>>> {
            httpClient.get("http://localhost:8080/room/distribution/$housekeeperId") {
                parameter("date", date)
            }
        }.map { it.data }

}