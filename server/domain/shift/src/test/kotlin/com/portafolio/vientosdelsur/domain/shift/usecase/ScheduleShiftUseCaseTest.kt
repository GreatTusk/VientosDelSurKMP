package com.portafolio.vientosdelsur.domain.shift.usecase

import com.f776.core.common.Result
import com.f776.core.common.takeOrNull
import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.domain.employee.FakeEmployeeRepository
import com.portafolio.vientosdelsur.domain.employee.repository.EmployeeRepository
import com.portafolio.vientosdelsur.domain.shift.model.EmployeeDaysOff
import com.portafolio.vientosdelsur.domain.shift.model.Shift
import com.portafolio.vientosdelsur.domain.shift.model.SundaysOff
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.*

class ScheduleShiftUseCaseTest : KoinTest {

    private lateinit var useCase: ScheduleShiftUseCase
    private lateinit var testEmployees: List<Employee>
    private val testDispatcher = StandardTestDispatcher()

    private companion object {
        // Test date constants
        val TEST_MONTH = LocalDate(2023, Month.MAY, 1)
        val SUNDAY_MAY_7 = LocalDate(2023, Month.MAY, 7)
        val SUNDAY_MAY_14 = LocalDate(2023, Month.MAY, 14)
        val SUNDAY_MAY_21 = LocalDate(2023, Month.MAY, 21)
        val SUNDAY_MAY_28 = LocalDate(2023, Month.MAY, 28)
        val MONDAY_MAY_8 = LocalDate(2023, Month.MAY, 8)
        val TUESDAY_MAY_9 = LocalDate(2023, Month.MAY, 9)

        // Expected values
        val ALL_SUNDAYS = listOf(SUNDAY_MAY_7, SUNDAY_MAY_14, SUNDAY_MAY_21, SUNDAY_MAY_28)
    }

    @BeforeTest
    fun init() = runBlocking {
        val module = module {
            singleOf(::FakeEmployeeRepository).bind<EmployeeRepository>()
            single<CoroutineDispatcher> { testDispatcher }
            singleOf(::ScheduleShiftUseCase)
        }
        startKoin {
            modules(module)
        }

        useCase = inject<ScheduleShiftUseCase>().value
        testEmployees = inject<EmployeeRepository>().value.allEmployees().takeOrNull() ?: error("Invalid state")
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `assignSundaysOff distributes Sundays evenly among employees`() {
        // Arrange - May 2023 has 4 Sundays: 7, 14, 21, 28

        // Act
        val result = useCase.assignSundaysOff(TEST_MONTH, testEmployees)

        // Assert
        assertEquals(testEmployees.size, result.size, "Each employee should have Sundays assigned")

        // Check that each employee has 2 Sundays off
        result.forEach { employeeDaysOff ->
            assertEquals(2, employeeDaysOff.sundaysOff.daysOff.size, "Each employee should have exactly 2 Sundays off")
        }

        // Verify each Sunday appears in the results
        ALL_SUNDAYS.forEach { sunday ->
            val count = result.count { it.sundaysOff.daysOff.contains(sunday) }
            assertTrue(count > 0, "Sunday $sunday should be assigned to at least one employee")
        }
    }

    @Test
    fun `assignShift respects Sundays off and day off`() {
        // Arrange
        val employee = testEmployees[0] // Cook with Sunday off
        val employeeDaysOff = EmployeeDaysOff(
            employee = employee,
            sundaysOff = SundaysOff(
                first = SUNDAY_MAY_7,
                second = SUNDAY_MAY_21
            )
        )

        val kitchenHours = mutableMapOf<LocalDate, Double>()

        // Act & Assert - Test day off (Sunday)
        val sundayShift = useCase.assignShift(employeeDaysOff, SUNDAY_MAY_7, kitchenHours)
        assertNull(sundayShift, "Should not assign shift on a Sunday off")

        // Act & Assert - Test regular day
        val mondayShift = useCase.assignShift(employeeDaysOff, MONDAY_MAY_8, kitchenHours)
        assertNotNull(mondayShift, "Should assign shift on a regular work day")
        assertEquals(Shift.GENERAL_DUTY, mondayShift)

        // Act & Assert - Test employee with Monday as day off
        val employeeWithMondayOff = EmployeeDaysOff(
            employee = testEmployees[1], // María has Monday off
            sundaysOff = SundaysOff(
                first = SUNDAY_MAY_7,
                second = SUNDAY_MAY_21
            )
        )

        val shiftForMaria = useCase.assignShift(employeeWithMondayOff, MONDAY_MAY_8, kitchenHours)
        assertNull(shiftForMaria, "Should not assign shift on employee's day off")
    }

    @Test
    fun `assignShift assigns cook to general duty and contributes full time hours`() {
        // Arrange
        val kitchenHours = mutableMapOf<LocalDate, Double>()
        val cookEmployee = testEmployees[0] // Juan (Cook)
        val cookDaysOff = EmployeeDaysOff(
            employee = cookEmployee,
            sundaysOff = SundaysOff(first = SUNDAY_MAY_7, second = SUNDAY_MAY_14)
        )

        // Act
        val cookShift = useCase.assignShift(cookDaysOff, TUESDAY_MAY_9, kitchenHours)

        // Assert
        assertEquals(Shift.GENERAL_DUTY, cookShift, "Cook should be assigned to general duty")
        assertEquals(ScheduleShiftUseCase.FULL_TIME_HOURS, kitchenHours[TUESDAY_MAY_9], "Cook should contribute full time hours")
    }

    @Test
    fun `assignShift assigns kitchen housekeeper to kitchen lead role`() {
        // Arrange
        val kitchenHours = mutableMapOf<LocalDate, Double>()
        val kitchenEmployee = testEmployees[1] // María (Kitchen)
        val kitchenDaysOff = EmployeeDaysOff(
            employee = kitchenEmployee,
            sundaysOff = SundaysOff(first = SUNDAY_MAY_7, second = SUNDAY_MAY_14)
        )

        // Act
        val kitchenShift = useCase.assignShift(kitchenDaysOff, TUESDAY_MAY_9, kitchenHours)

        // Assert
        assertEquals(Shift.KITCHEN_LEAD, kitchenShift, "Kitchen housekeeper should lead kitchen")
        assertEquals(ScheduleShiftUseCase.FULL_TIME_HOURS, kitchenHours[TUESDAY_MAY_9], "Kitchen lead should contribute full time hours")
    }

    @Test
    fun `assignShift assigns general duty when kitchen hours are filled`() {
        // Arrange
        val kitchenHours = mutableMapOf<LocalDate, Double>()
        kitchenHours[TUESDAY_MAY_9] = ScheduleShiftUseCase.FULL_TIME_HOURS // Kitchen hours already filled

        val kitchenEmployee = testEmployees[1] // María (Kitchen)
        val kitchenDaysOff = EmployeeDaysOff(
            employee = kitchenEmployee,
            sundaysOff = SundaysOff(first = SUNDAY_MAY_7, second = SUNDAY_MAY_14)
        )

        // Act
        val kitchenShift = useCase.assignShift(kitchenDaysOff, TUESDAY_MAY_9, kitchenHours)

        // Assert
        assertEquals(
            Shift.GENERAL_DUTY,
            kitchenShift,
            "Kitchen worker should get general duty when kitchen is filled"
        )
        assertEquals(ScheduleShiftUseCase.FULL_TIME_HOURS, kitchenHours[TUESDAY_MAY_9], "Hours shouldn't change for general duty")
    }

    @Test
    fun `invoke returns mapped results from repository`() = runTest(testDispatcher) {
        // Act
        val result = useCase(TEST_MONTH)

        // Assert
        assertTrue(result is Result.Success, "Result should be successful")
        val scheduleMap = result.data

        // Verify all employees are in the schedule
        assertEquals(testEmployees.size, scheduleMap.keys.size, "All employees should be scheduled")

        // Verify each employee has shifts assigned
        scheduleMap.forEach { (_, shifts) ->
            assertTrue(shifts.isNotEmpty(), "Each employee should have some shifts")

            // Each shift should have a valid date in May 2023
            shifts.forEach { shiftDate ->
                assertEquals(2023, shiftDate.date.year, "Year should be 2023")
                assertEquals(Month.MAY, shiftDate.date.month, "Month should be May")
            }
        }
    }

    @Test
    fun `scheduleMonthlyShifts assigns shifts for all working days`() = runTest(testDispatcher) {
        // Act
        val result = useCase.scheduleMonthlyShifts(TEST_MONTH, testEmployees)

        // Assert - Verify all employees have shifts assigned
        assertEquals(testEmployees.size, result.size, "All employees should have scheduled shifts")

        result.forEach { (employeeDaysOff, shiftDates) ->
            // Employee should not have shifts on their day off or Sundays off
            shiftDates.forEach { shiftDate ->
                assertNotEquals(
                    shiftDate.date.dayOfWeek,
                    employeeDaysOff.employee.data.dayOff,
                    "Employee should not work on their day off"
                )
                assertFalse(
                    shiftDate.date in employeeDaysOff.sundaysOff.daysOff,
                    "Employee should not work on their Sundays off"
                )
            }
        }
    }
}