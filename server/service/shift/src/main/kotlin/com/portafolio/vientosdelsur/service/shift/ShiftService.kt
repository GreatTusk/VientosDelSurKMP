package com.portafolio.vientosdelsur.service.shift

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.shared.dto.BaseResponseDto
import com.portafolio.vientosdelsur.shared.dto.employee.EmployeeDto
import com.portafolio.vientosdelsur.shared.dto.shift.EmployeeScheduleDto
import kotlinx.datetime.LocalDate

interface ShiftService {
    suspend fun getEmployeesWorkingOn(date: LocalDate): Result<BaseResponseDto<List<EmployeeDto>>, DataError.Remote>
    suspend fun getMonthlyShiftsFor(employeeId: Int): Result<BaseResponseDto<EmployeeScheduleDto>, DataError.Remote>
}