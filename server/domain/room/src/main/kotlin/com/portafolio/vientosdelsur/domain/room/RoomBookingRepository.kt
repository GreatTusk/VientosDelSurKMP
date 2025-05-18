package com.portafolio.vientosdelsur.domain.room

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.domain.room.model.RoomBookingId
import kotlinx.datetime.LocalDate

interface RoomBookingRepository {
    suspend fun getBookedRoomsOn(
        date: LocalDate
    ): Result<List<RoomBookingId>, DataError.Remote>
}