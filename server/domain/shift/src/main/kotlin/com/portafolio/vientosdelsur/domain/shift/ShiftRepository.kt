package com.portafolio.vientosdelsur.domain.shift

import com.f776.core.common.DataError
import com.f776.core.common.EmptyResult
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.domain.employee.Occupation
import com.portafolio.vientosdelsur.domain.shift.model.EmployeeDaysOff
import com.portafolio.vientosdelsur.domain.shift.model.HousekeeperShift
import com.portafolio.vientosdelsur.domain.shift.model.ShiftDate
import kotlinx.datetime.LocalDate

interface ShiftRepository {
    suspend fun saveAll(shifts: Map<EmployeeDaysOff, List<ShiftDate>>): EmptyResult<DataError.Remote>
    suspend fun getMonthlyShifts(
        startDate: LocalDate,
        endDate: LocalDate,
        occupation: Occupation
    ): Result<Map<LocalDate, List<HousekeeperShift>>, DataError.Remote>

    suspend fun getMonthlyShiftsFor(
        employeeId: Int,
        startDate: LocalDate,
        endDate: LocalDate
    ): Result<Map<EmployeeDaysOff, List<ShiftDate>>, DataError.Remote>
}