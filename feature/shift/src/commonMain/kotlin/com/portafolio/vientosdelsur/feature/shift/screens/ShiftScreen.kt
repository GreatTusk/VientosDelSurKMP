package com.portafolio.vientosdelsur.feature.shift.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.f776.core.ui.theme.VientosDelSurTheme
import com.portafolio.vientosdelsur.domain.shift.EmployeeSchedule
import com.portafolio.vientosdelsur.domain.shift.Shift
import com.portafolio.vientosdelsur.domain.shift.ShiftDate
import com.portafolio.vientosdelsur.feature.shift.screens.components.EmployeeScheduleCalendar
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun ShiftScreenRoot(modifier: Modifier = Modifier, shiftViewModel: ShiftViewModel = koinViewModel()) {
    val employeeSchedule by shiftViewModel.employeeSchedule.collectAsStateWithLifecycle()
    ShiftScreen(employeeSchedule = employeeSchedule)
}

@Composable
private fun ShiftScreen(modifier: Modifier = Modifier, employeeSchedule: EmployeeSchedule) {
    EmployeeScheduleCalendar(employeeSchedule)
}

@Preview
@Composable
private fun ShiftScreenPreview() {
    val mockSchedule = EmployeeSchedule(
        workingDays = listOf(
            ShiftDate(
                shift = Shift(
                    startTime = LocalTime.parse("08:00:00"),
                    endTime = LocalTime.parse("16:00:00"),
                    type = "Morning"
                ),
                date = LocalDate.parse("2025-06-20")
            ),
            ShiftDate(
                shift = Shift(
                    startTime = LocalTime.parse("14:00:00"),
                    endTime = LocalTime.parse("22:00:00"),
                    type = "Evening"
                ),
                date = LocalDate.parse("2025-06-21")
            )
        ),
        daysOff = listOf(
            LocalDate.parse("2025-06-22"),
            LocalDate.parse("2025-06-23")
        )
    )

    VientosDelSurTheme {
        ShiftScreen(employeeSchedule = mockSchedule)
    }
}