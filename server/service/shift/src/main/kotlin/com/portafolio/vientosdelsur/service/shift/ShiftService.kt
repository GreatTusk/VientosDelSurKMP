package com.portafolio.vientosdelsur.service.shift

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.shared.dto.MonthlyShiftDistributionDto

interface ShiftService {
    suspend fun scheduleShifts(): Result<List<MonthlyShiftDistributionDto>, DataError.Remote>
}