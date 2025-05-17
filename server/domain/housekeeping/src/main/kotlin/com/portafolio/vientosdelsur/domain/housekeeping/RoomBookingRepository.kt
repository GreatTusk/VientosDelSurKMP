package com.portafolio.vientosdelsur.domain.housekeeping

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.domain.housekeeping.model.RoomBooking
import kotlinx.datetime.LocalDate

interface RoomBookingRepository {
    suspend fun getBookedRoomsOn(
        startDate: LocalDate,
        endDate: LocalDate
    ): Result<Map<LocalDate, List<RoomBooking>>, DataError.Remote>
}