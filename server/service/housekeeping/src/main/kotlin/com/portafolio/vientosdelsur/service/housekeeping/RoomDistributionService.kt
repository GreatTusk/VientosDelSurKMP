package com.portafolio.vientosdelsur.service.housekeeping

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.shared.dto.room.MonthlyRoomDistributionDto
import kotlinx.datetime.LocalDate

interface RoomDistributionService {
    suspend fun distributeRoomsMonth(date: LocalDate): Result<MonthlyRoomDistributionDto, DataError.Remote>
}