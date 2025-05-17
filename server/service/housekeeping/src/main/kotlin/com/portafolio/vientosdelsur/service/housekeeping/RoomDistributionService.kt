package com.portafolio.vientosdelsur.service.housekeeping

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.shared.dto.MonthlyRoomDistributionDto

interface RoomDistributionService {
    suspend fun distributeRoomsMonth(): Result<MonthlyRoomDistributionDto, DataError.Remote>
}