package com.portafolio.vientosdelsur.domain.shift.usecase

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.f776.core.common.map
import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.domain.employee.HousekeeperRole
import com.portafolio.vientosdelsur.domain.employee.repository.EmployeeRepository
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
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

class ScheduleShiftUseCase(
    private val employeeRepository: EmployeeRepository,
    private val defaultDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(date: LocalDate): Result<Map<EmployeeDaysOff, List<ShiftDate>>, DataError.Remote> {
        val employeeResult = employeeRepository.allEmployees()
        return employeeResult.map { scheduleMonthlyShifts(month = date, employees = it) }
    }

    private suspend fun scheduleMonthlyShifts(
        month: LocalDate,
        employees: List<Employee>
    ): Map<EmployeeDaysOff, List<ShiftDate>> = withContext(defaultDispatcher) {
        val employeeShifts = assignSundaysOff(month, employees)

        employeeShifts.map { employee ->
            async {
                employee to month.workingDays.mapNotNull { date ->
                    assignShift(employee, date)?.let { ShiftDate(it, date) }
                }.toList()
            }
        }.awaitAll().toMap()
    }

    fun assignSundaysOff(month: LocalDate, employees: List<Employee>): List<EmployeeDaysOff> {
        val sundays = month.sundays

        return employees.mapIndexed { i, employee ->
            val first = sundays[i % sundays.size]
            EmployeeDaysOff(
                employee = employee,
                sundaysOff = SundaysOff(
                    first = first,
                    second = first.plus(1, DateTimeUnit.WEEK)
                )
            )
        }
    }

    private fun assignShift(employeeDaysOff: EmployeeDaysOff, date: LocalDate): Shift? {
        if (date in employeeDaysOff.sundaysOff.daysOff || date.dayOfWeek == employeeDaysOff.employee.data.dayOff) return null

        return when (val employee = employeeDaysOff.employee) {
            is Employee.Housekeeper -> {
                when (employee.housekeeperRole) {
                    HousekeeperRole.KITCHEN -> Shift.KITCHEN_LEAD
                    HousekeeperRole.KITCHEN_SUPPORT -> Shift.KITCHEN_ASSISTANT
                    // TODO: Maybe consider
                    HousekeeperRole.ON_CALL -> null
                }
            }

            else -> Shift.GENERAL_DUTY

        }
    }
}