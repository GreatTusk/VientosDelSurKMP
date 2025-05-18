package com.portafolio.vientosdelsur.domain.housekeeping

import com.f776.core.common.DataError
import com.f776.core.common.EmptyResult
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.domain.employee.Occupation
import com.portafolio.vientosdelsur.domain.housekeeping.model.HousekeeperShift
import com.portafolio.vientosdelsur.domain.housekeeping.usecase.MonthlyRoomDistribution
import kotlinx.datetime.LocalDate

interface HousekeeperShiftRepository {
    suspend fun saveAll(distribution: MonthlyRoomDistribution): EmptyResult<DataError.Remote>

    suspend fun getMonthlyShifts(
        startDate: LocalDate,
        endDate: LocalDate,
        occupation: Occupation
    ): Result<Map<LocalDate, List<HousekeeperShift>>, DataError.Remote>
}