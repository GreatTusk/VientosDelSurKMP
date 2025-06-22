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

    suspend operator fun invoke(date: LocalDate): Result<Map<EmployeeDaysOff, List<ShiftDate>>, DataError.Remote> {
        val employeeResult = employeeRepository.allEmployees()
        return employeeResult.map { scheduleMonthlyShifts(month = date, employees = it) }
    }

    internal suspend fun scheduleMonthlyShifts(
        month: LocalDate,
        employees: List<Employee>
    ): Map<EmployeeDaysOff, List<ShiftDate>> = withContext(defaultDispatcher) {
        val employeeShifts = assignSundaysOff(month, employees)

        // Process each day in parallel
        val dailyShifts = month.workingDays.map { date ->
            async {
                date to processDayShifts(date, employeeShifts)
            }
        }.toList().awaitAll().toMap()

        employeeShifts.associateWith { employee ->
            dailyShifts.mapNotNull { (date, shifts) ->
                shifts[employee]?.let { shift -> ShiftDate(shift, date) }
            }
        }
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

    private fun processDayShifts(
        date: LocalDate,
        employeeShifts: List<EmployeeDaysOff>
    ): Map<EmployeeDaysOff, Shift> {
        var kitchenHours = 0.0

        return employeeShifts.mapNotNull { employeeDaysOff ->
            val shift = assignShift(employeeDaysOff, date, kitchenHours)

            if (shift != null) {
                kitchenHours += when (shift) {
                    Shift.KITCHEN_LEAD -> FULL_TIME_HOURS
                    Shift.KITCHEN_ASSISTANT -> MIXED_KITCHEN_HOURS
                    else -> 0.0
                }
                employeeDaysOff to shift
            } else {
                null
            }
        }.toMap()
    }

    internal fun assignShift(
        employeeDaysOff: EmployeeDaysOff,
        date: LocalDate,
        currentKitchenHours: Double
    ): Shift? {
        if (date in employeeDaysOff.sundaysOff.daysOff || date.dayOfWeek == employeeDaysOff.employee.data.dayOff)
            return null

        return when (val employee = employeeDaysOff.employee) {
            is Employee.Housekeeper -> {
                when (employee.housekeeperRole) {
                    HousekeeperRole.KITCHEN -> {
                        if (currentKitchenHours < FULL_TIME_HOURS) {
                            Shift.KITCHEN_LEAD
                        } else {
                            Shift.GENERAL_DUTY
                        }
                    }

                    HousekeeperRole.KITCHEN_SUPPORT -> {
                        val isMixedWeek = date.isSameWeekAs(employeeDaysOff.sundaysOff.first)
                        if (isMixedWeek) {
                            Shift.KITCHEN_ASSISTANT
                        } else {
                            Shift.GENERAL_DUTY
                        }
                    }

                    HousekeeperRole.ON_CALL -> Shift.GENERAL_DUTY
                }
            }

            is Employee.Cook -> Shift.GENERAL_DUTY

            else -> Shift.GENERAL_DUTY
        }
    }

    internal companion object {
        internal const val FULL_TIME_HOURS = 7.0
        internal const val MIXED_KITCHEN_HOURS = 3.0
    }
}