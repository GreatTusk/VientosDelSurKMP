package com.portafolio.vientosdelsur.service.housekeeping

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.domain.housekeeping.usecase.DistributeRoomsUseCase
import com.portafolio.vientosdelsur.service.housekeeping.mapper.toRoomDistributionDto
import com.portafolio.vientosdelsur.shared.dto.MonthlyRoomDistributionDto
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

internal class RoomDistributionServiceImpl(
    private val distributeRoomsUseCase: DistributeRoomsUseCase
) : RoomDistributionService {
    override suspend fun distributeRoomsMonth(): Result<MonthlyRoomDistributionDto, DataError.Remote> {
        val distribution =
            distributeRoomsUseCase(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date)
        return Result.Success(distribution.toRoomDistributionDto())
    }
}