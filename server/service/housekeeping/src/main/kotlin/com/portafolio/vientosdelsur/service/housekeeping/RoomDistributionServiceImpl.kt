package com.portafolio.vientosdelsur.service.housekeeping

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.f776.core.common.map
import com.portafolio.vientosdelsur.domain.housekeeping.HousekeeperShiftRepository
import com.portafolio.vientosdelsur.domain.housekeeping.usecase.DistributeRoomsUseCase
import com.portafolio.vientosdelsur.service.housekeeping.mapper.toRoomDistributionDto
import com.portafolio.vientosdelsur.shared.dto.MonthlyRoomDistributionDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

internal class RoomDistributionServiceImpl(
    private val distributeRoomsUseCase: DistributeRoomsUseCase,
    private val housekeeperShiftRepository: HousekeeperShiftRepository,
    private val coroutineScope: CoroutineScope
) : RoomDistributionService {
    override suspend fun distributeRoomsMonth(): Result<MonthlyRoomDistributionDto, DataError.Remote> {
        return distributeRoomsUseCase(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date)
            .map { distribution ->
                coroutineScope.launch {
                    housekeeperShiftRepository.saveAll(distribution)
                }
                distribution.toRoomDistributionDto()
            }

    }
}