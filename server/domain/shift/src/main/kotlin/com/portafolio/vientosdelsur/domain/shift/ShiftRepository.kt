package com.portafolio.vientosdelsur.domain.shift

import com.f776.core.common.DataError
import com.f776.core.common.EmptyResult
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.domain.shift.model.EmployeeDaysOff
import com.portafolio.vientosdelsur.domain.shift.model.EmployeeSchedule
import com.portafolio.vientosdelsur.domain.shift.model.ShiftDate
import kotlinx.datetime.LocalDate

interface ShiftRepository {
    suspend fun saveAll(shifts: Map<EmployeeDaysOff, List<ShiftDate>>): EmptyResult<DataError.Remote>

    suspend fun getShiftsDuring(
        employeeId: Int,
        during: ClosedRange<LocalDate>
    ): Result<EmployeeSchedule, DataError.Remote>

    suspend fun getEmployeesWorkingOn(date: LocalDate): Result<List<Employee>, DataError.Remote>
}