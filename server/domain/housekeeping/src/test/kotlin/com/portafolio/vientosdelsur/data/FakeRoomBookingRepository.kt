package com.portafolio.vientosdelsur.data

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.domain.room.RoomBookingRepository
import com.portafolio.vientosdelsur.domain.room.model.RoomBookingId
import com.portafolio.vientosdelsur.domain.room.model.RoomCleaningType
import kotlinx.datetime.LocalDate

class FakeRoomBookingRepository: RoomBookingRepository {
    override suspend fun getBookedRoomsOn(date: LocalDate): Result<List<RoomBookingId>, DataError.Remote> {
        val bookings = (0..28).map { index ->
            val cleaningType = if (index % 2 == 0) RoomCleaningType.ROOM else RoomCleaningType.GUEST
            val workUnits = when (cleaningType) {
                RoomCleaningType.ROOM -> (40..50).random()
                RoomCleaningType.GUEST -> (30..40).random()
            }
            RoomBookingId(
                roomId = index,
                workUnits = workUnits,
                cleaningType = cleaningType
            )
        }
        return Result.Success(bookings)
    }
}