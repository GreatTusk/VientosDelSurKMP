package com.portafolio.vientosdelsur.service.shift

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.f776.core.common.flatMap
import com.f776.core.common.map
import com.portafolio.vientosdelsur.domain.shift.ShiftRepository
import com.portafolio.vientosdelsur.domain.shift.workingDaysRange
import com.portafolio.vientosdelsur.service.employee.mapper.toEmployeeDto
import com.portafolio.vientosdelsur.service.shift.mapper.toEmployeeScheduleDto
import com.portafolio.vientosdelsur.shared.dto.BaseResponseDto
import com.portafolio.vientosdelsur.shared.dto.employee.EmployeeDto
import com.portafolio.vientosdelsur.shared.dto.shift.EmployeeScheduleDto
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

internal class ShiftServiceImpl(private val shiftRepository: ShiftRepository) : ShiftService {
    override suspend fun getEmployeesWorkingOn(date: LocalDate): Result<BaseResponseDto<List<EmployeeDto>>, DataError.Remote> {
        return shiftRepository.getEmployeesWorkingOn(date)
            .flatMap { it.toEmployeeDto() }
            .map {
                BaseResponseDto(
                    message = "Retrieved successfully",
                    data = it
                )
            }
    }

    override suspend fun getMonthlyShiftsFor(employeeId: Int): Result<BaseResponseDto<EmployeeScheduleDto>, DataError.Remote> {
        val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        return shiftRepository.getShiftsDuring(employeeId, currentDate.workingDaysRange)
            .map { it.toEmployeeScheduleDto() }
            .map {
                BaseResponseDto(
                    message = "Retrieved successfully",
                    data = it
                )
            }
    }

    override suspend fun getMonthlyShifts(): Result<BaseResponseDto<Map<EmployeeDto.Get, EmployeeScheduleDto>>, DataError.Remote> {
        val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

        return shiftRepository.getMonthlyShifts(currentDate.workingDaysRange)
            .map { shifts ->
                shifts
                    .mapKeys { (key, _) -> key.toEmployeeDto() }
                    .mapValues { (_, value) -> value.toEmployeeScheduleDto() }
            }
            .map {
                BaseResponseDto(
                    message = "Retrieved successfully",
                    data = it
                )
            }
    }
}