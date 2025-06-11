package com.portafolio.vientosdelsur.service.shift

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.shared.dto.BaseResponseDto
import com.portafolio.vientosdelsur.shared.dto.employee.EmployeeDto
import com.portafolio.vientosdelsur.shared.dto.room.MonthlyShiftDistributionDto
import kotlinx.datetime.LocalDate

typealias MonthlyShiftDistributionResponse = BaseResponseDto<List<MonthlyShiftDistributionDto>>

interface ShiftSchedulerService {
    suspend fun scheduleShifts(): Result<MonthlyShiftDistributionResponse, DataError.Remote>
    suspend fun getEmployeesWorkingOn(date: LocalDate): Result<BaseResponseDto<List<EmployeeDto>>, DataError.Remote>
}