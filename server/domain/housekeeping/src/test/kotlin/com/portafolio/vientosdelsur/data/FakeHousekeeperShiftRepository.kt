package com.portafolio.vientosdelsur.data

import com.f776.core.common.DataError
import com.f776.core.common.EmptyResult
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.domain.employee.*
import com.portafolio.vientosdelsur.domain.housekeeping.HousekeeperShiftRepository
import com.portafolio.vientosdelsur.domain.housekeeping.model.HousekeeperShift
import com.portafolio.vientosdelsur.domain.housekeeping.usecase.MonthlyRoomDistribution
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

class FakeHousekeeperShiftRepository : HousekeeperShiftRepository {

    private val fakeHousekeepers = listOf(
        Employee.Housekeeper(
            data = BaseEmployee(
                id = 1,
                firstName = "Alice",
                lastName = "Johnson",
                phoneNumber = "123-456-7890",
                dayOff = DayOfWeek.SUNDAY,
                hireDate = LocalDate(2022, 5, 1)
            ),
            housekeeperRole = HousekeeperRole.KITCHEN_SUPPORT,
            preferredFloor = Floor(1)
        ),
        Employee.Housekeeper(
            data = BaseEmployee(
                id = 2,
                firstName = "Bob",
                lastName = "Smith",
                phoneNumber = "987-654-3210",
                dayOff = DayOfWeek.TUESDAY,
                hireDate = LocalDate(2023, 1, 15)
            ),
            housekeeperRole = HousekeeperRole.KITCHEN,
            preferredFloor = Floor(2)
        )
    )

    override suspend fun saveAll(distribution: MonthlyRoomDistribution): EmptyResult<DataError.Remote> {
        TODO("Not yet implemented")
    }

    override suspend fun getMonthlyShifts(
        startDate: LocalDate,
        endDate: LocalDate,
        occupation: Occupation
    ): Result<Map<LocalDate, List<HousekeeperShift>>, DataError.Remote> {
        if (occupation != Occupation.HOUSEKEEPER) {
            return Result.Success(emptyMap())
        }

        val shiftsByDay = mutableMapOf<LocalDate, List<HousekeeperShift>>()
        var shiftIdCounter = 1

        val datesInRange = generateDateRange(startDate, endDate)

        for (date in datesInRange) {
            val shiftsForDay = fakeHousekeepers
                .filter { it.data.dayOff != date.dayOfWeek }
                .map { housekeeper ->
                    HousekeeperShift(
                        workShiftId = shiftIdCounter++,
                        employee = housekeeper,
                        workMinutes = (240..480).random()
                    )
                }

            if (shiftsForDay.isNotEmpty()) {
                shiftsByDay[date] = shiftsForDay
            }
        }

        return Result.Success(shiftsByDay)
    }

    private fun generateDateRange(start: LocalDate, end: LocalDate): List<LocalDate> {
        val dates = mutableListOf<LocalDate>()
        var current = start
        while (current <= end) {
            dates.add(current)
            current = current.plus(1, DateTimeUnit.DAY)
        }
        return dates
    }
}
