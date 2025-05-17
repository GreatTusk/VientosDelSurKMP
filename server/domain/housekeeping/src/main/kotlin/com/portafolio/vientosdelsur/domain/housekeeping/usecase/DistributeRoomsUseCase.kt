package com.portafolio.vientosdelsur.domain.housekeeping.usecase

import com.portafolio.vientosdelsur.domain.employee.repository.HousekeeperRepository
import com.portafolio.vientosdelsur.domain.housekeeping.RoomRepository

class DistributeRoomsUseCase(
    private val roomRepository: RoomRepository,
    private val housekeeperRepository: HousekeeperRepository
) {
    suspend operator fun invoke() {

    }

    private suspend fun getRoomsToday() {
    }
}