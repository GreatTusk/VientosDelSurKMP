package com.portafolio.vientosdelsur.domain.shift.usecase

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.f776.core.common.map
import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.domain.employee.HousekeeperRole
import com.portafolio.vientosdelsur.domain.employee.repository.EmployeeRepository
import com.portafolio.vientosdelsur.domain.shift.isSameWeekAs
import com.portafolio.vientosdelsur.domain.shift.model.EmployeeDaysOff
import com.portafolio.vientosdelsur.domain.shift.model.Shift
import com.portafolio.vientosdelsur.domain.shift.model.ShiftDate
import com.portafolio.vientosdelsur.domain.shift.model.SundaysOff
import com.portafolio.vientosdelsur.domain.shift.sundays
import com.portafolio.vientosdelsur.domain.shift.workingDays
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate

class ScheduleShiftUseCase(
    private val employeeRepository: EmployeeRepository,
    private val defaultDispatcher: CoroutineDispatcher
) {

    private data class DailyShiftCount(
        var kitchenHours: Double = 0.0,
        var housekeepingHours: Double = 0.0
    )

    suspend operator fun invoke(date: LocalDate): Result<Map<EmployeeDaysOff, List<ShiftDate>>, DataError.Remote> {
        val employeeResult = employeeRepository.allEmployees()
        return employeeResult.map { scheduleMonthlyShifts(month = date, employees = it) }
    }

    private suspend fun scheduleMonthlyShifts(
        month: LocalDate,
        employees: List<Employee>
    ): Map<EmployeeDaysOff, List<ShiftDate>> = withContext(defaultDispatcher) {
        val employeeShifts = assignSundaysOff(month, employees)
        val shiftCountsPerDay = mutableMapOf<LocalDate, DailyShiftCount>()

        employeeShifts.map { employee ->
            async {
                employee to month.workingDays.mapNotNull { date ->
                    assignShift(employee, date, shiftCountsPerDay)?.let { ShiftDate(it, date) }
                }.toList()
            }
        }.awaitAll().toMap()
    }

    internal fun assignSundaysOff(month: LocalDate, employees: List<Employee>): List<EmployeeDaysOff> {
        val sundays = month.sundays

        return employees.mapIndexed { i, employee ->
            val first = sundays[i % sundays.size]
            EmployeeDaysOff(
                employee = employee,
                sundaysOff = SundaysOff(
                    first = first,
                    second = sundays[(i + 1) % sundays.size]
                )
            )
        }
    }

    private fun assignShift(
        employeeDaysOff: EmployeeDaysOff,
        date: LocalDate,
        shiftCounts: MutableMap<LocalDate, DailyShiftCount>
    ): Shift? {
        if (date in employeeDaysOff.sundaysOff.daysOff || date.dayOfWeek == employeeDaysOff.employee.data.dayOff) return null

        val count = shiftCounts.getOrPut(date) { DailyShiftCount() }

        return when (val employee = employeeDaysOff.employee) {
            is Employee.Housekeeper -> {
                when (employee.housekeeperRole) {
                    HousekeeperRole.KITCHEN -> {
                        count.kitchenHours += FULL_TIME_HOURS
                        Shift.KITCHEN_LEAD
                    }

                    HousekeeperRole.KITCHEN_SUPPORT -> {
                        val isMixedWeek = date.isSameWeekAs(employeeDaysOff.sundaysOff.first)
                        if (isMixedWeek) {
                            count.kitchenHours += MIXED_KITCHEN_HOURS
                            count.housekeepingHours += MIXED_HOUSEKEEPING_HOURS
                            Shift.KITCHEN_ASSISTANT
                        } else {
                            count.housekeepingHours += FULL_TIME_HOURS
                            Shift.GENERAL_DUTY
                        }
                    }

                    HousekeeperRole.ON_CALL -> null
                }
            }

            is Employee.Cook -> {
                count.kitchenHours += FULL_TIME_HOURS
                Shift.GENERAL_DUTY
            }

            else -> {
                Shift.GENERAL_DUTY
            }
        }
    }


    companion object {
        private const val FULL_TIME_HOURS = 7.0
        private const val MIXED_KITCHEN_HOURS = 3.0
        private const val MIXED_HOUSEKEEPING_HOURS = 4.0
    }
}