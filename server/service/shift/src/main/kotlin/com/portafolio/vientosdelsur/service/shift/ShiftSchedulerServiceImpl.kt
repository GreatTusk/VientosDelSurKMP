package com.portafolio.vientosdelsur.service.shift

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.f776.core.common.flatMap
import com.f776.core.common.map
import com.portafolio.vientosdelsur.domain.shift.ShiftRepository
import com.portafolio.vientosdelsur.domain.shift.usecase.ScheduleShiftUseCase
import com.portafolio.vientosdelsur.service.employee.mapper.toEmployeeDto
import com.portafolio.vientosdelsur.service.shift.mapper.toMonthlyShiftsDto
import com.portafolio.vientosdelsur.shared.dto.BaseResponseDto
import com.portafolio.vientosdelsur.shared.dto.employee.EmployeeDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

internal class ShiftSchedulerServiceImpl(
    private val scheduleShiftUseCase: ScheduleShiftUseCase,
    private val shiftRepository: ShiftRepository,
    private val coroutineScope: CoroutineScope
) : ShiftSchedulerService {
    override suspend fun scheduleShifts(): Result<MonthlyShiftDistributionResponse, DataError.Remote> {
        return scheduleShiftUseCase(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date)
            .map { shifts ->
                coroutineScope.launch {
                    shiftRepository.saveAll(shifts)
                }
                shifts.map { it.toMonthlyShiftsDto() }
            }.map {
                BaseResponseDto(message = "Distribution as follows", data = it)
            }
    }
}