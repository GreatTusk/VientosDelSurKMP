package com.portafolio.vientosdelsur.domain.housekeeping

import com.f776.core.common.DataError
import com.f776.core.common.EmptyResult
import com.portafolio.vientosdelsur.domain.housekeeping.usecase.MonthlyRoomDistribution

interface HousekeeperShiftRepository {
    suspend fun saveAll(distribution: MonthlyRoomDistribution): EmptyResult<DataError.Remote>
}