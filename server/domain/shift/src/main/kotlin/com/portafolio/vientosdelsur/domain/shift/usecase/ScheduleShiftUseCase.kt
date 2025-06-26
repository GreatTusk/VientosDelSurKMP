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
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
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
        val kitchenHours = mutableMapOf<LocalDate, Double>()
        val mutex = Mutex()

        employeeShifts.map { employee ->
            async {
                val shifts = mutableListOf<ShiftDate>()

                for (date in month.workingDays) {
                    val shift = assignShift(
                        employeeDaysOff = employee,
                        date = date,
                        kitchenHours = kitchenHours,
                        mutex = mutex
                    )
                    if (shift != null) {
                        shifts.add(ShiftDate(shift, date))
                    }
                }

                employee to shifts
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

    internal suspend fun assignShift(
        employeeDaysOff: EmployeeDaysOff,
        date: LocalDate,
        kitchenHours: MutableMap<LocalDate, Double>,
        mutex: Mutex
    ): Shift? {
        if (date in employeeDaysOff.sundaysOff.daysOff || date.dayOfWeek == employeeDaysOff.employee.data.dayOff)
            return null

        var count = mutex.withLock {
            kitchenHours.getOrPut(date) { 0.0 }
        }

        return when (val employee = employeeDaysOff.employee) {
            is Employee.Housekeeper -> {
                when (employee.housekeeperRole) {
                    HousekeeperRole.KITCHEN -> {
                        if (count < FULL_TIME_HOURS) {
                            count += FULL_TIME_HOURS
                            Shift.KITCHEN_LEAD
                        } else {
                            Shift.GENERAL_DUTY
                        }
                    }

                    HousekeeperRole.KITCHEN_SUPPORT -> {
                        val isMixedWeek = date.isSameWeekAs(employeeDaysOff.sundaysOff.first)
                        if (isMixedWeek) {
                            count += MIXED_KITCHEN_HOURS
                            Shift.KITCHEN_ASSISTANT
                        } else {
                            Shift.GENERAL_DUTY
                        }
                    }

                    HousekeeperRole.ON_CALL -> Shift.GENERAL_DUTY
                }
            }

            is Employee.Cook -> {
                count += FULL_TIME_HOURS
                Shift.GENERAL_DUTY
            }

            else -> {
                Shift.GENERAL_DUTY
            }
        }.also {
            mutex.withLock { kitchenHours[date] = count }
        }
    }


    internal companion object {
        internal const val FULL_TIME_HOURS = 7.0
        internal const val MIXED_KITCHEN_HOURS = 3.0
    }
}