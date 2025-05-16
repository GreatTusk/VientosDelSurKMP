package com.portafolio.vientosdelsur.domain.shift.usecase

import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.domain.shift.model.EmployeeShift
import com.portafolio.vientosdelsur.domain.shift.model.SundaysOff
import com.portafolio.vientosdelsur.domain.shift.sundays
import com.portafolio.vientosdelsur.domain.shift.workingDays
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

class ScheduleShiftUseCase {
    fun scheduleMonthlyShifts(month: LocalDate, employees: List<Employee>) {
        val employeeShifts = assignSundaysOff(month, employees)

        month.workingDays.map { date ->
            employeeShifts.map { assignShift(it, date) }
        }
    }

    fun assignSundaysOff(month: LocalDate, employees: List<Employee>): List<EmployeeShift> {
        val sundays = month.sundays

        return employees.mapIndexed { i, employee ->
            val first = sundays[i % sundays.size]
            EmployeeShift(
                employee = employee,
                sundaysOff = SundaysOff(
                    first = first,
                    second = first.plus(1, DateTimeUnit.WEEK)
                )
            )
        }
    }

    fun assignShift(employeeShift: EmployeeShift, date: LocalDate): Int? {
        if (date in employeeShift.sundaysOff.daysOff || date.dayOfWeek == employeeShift.employee.dayOff) return null

        when(employeeShift.employee.occupation) {

        }
        TODO()
    }
}