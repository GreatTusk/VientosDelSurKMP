package com.portafolio.vientosdelsur.service.shift

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.shared.dto.BaseResponseDto
import com.portafolio.vientosdelsur.shared.dto.employee.EmployeeDto
import com.portafolio.vientosdelsur.shared.dto.room.MonthlyShiftDistributionDto
import com.portafolio.vientosdelsur.shared.dto.shift.ScheduleDto
import kotlinx.datetime.LocalDate

interface ShiftService {
    suspend fun getEmployeesWorkingOn(date: LocalDate): Result<BaseResponseDto<List<EmployeeDto>>, DataError.Remote>
    suspend fun getMonthlyShiftsFor(employeeId: Int): Result<BaseResponseDto<ScheduleDto>, DataError.Remote>
    suspend fun getMonthlyShifts(): Result<BaseResponseDto<List<MonthlyShiftDistributionDto>>, DataError.Remote>
}