package com.portafolio.vientosdelsur.domain.shift

import com.f776.core.common.DataError
import com.f776.core.common.EmptyResult
import com.f776.core.common.Result

interface ShiftSchedulingRepository {
    suspend fun generateDraft(): Result<List<EmployeeSchedule>, DataError.Remote>
    suspend fun saveDraft(shiftSchedule: List<EmployeeSchedule>): EmptyResult<DataError.Remote>
}