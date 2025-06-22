@file:OptIn(ExperimentalMaterial3Api::class)

package com.portafolio.vientosdelsur.feature.shift.screens.employee

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.f776.core.ui.theme.VientosDelSurTheme
import com.portafolio.vientosdelsur.domain.shift.EmployeeSchedule
import com.portafolio.vientosdelsur.domain.shift.Shift
import com.portafolio.vientosdelsur.domain.shift.ShiftDate
import com.portafolio.vientosdelsur.domain.shift.ShiftType
import com.portafolio.vientosdelsur.feature.shift.screens.components.EmployeeScheduleCalendar
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun ShiftScreenRoot(modifier: Modifier = Modifier, shiftViewModel: ShiftViewModel = koinViewModel()) {
    val employeeSchedule by shiftViewModel.employeeSchedule.collectAsStateWithLifecycle()
    ShiftScreen(modifier = modifier, employeeSchedule = employeeSchedule)
}

@Composable
private fun ShiftScreen(modifier: Modifier = Modifier, employeeSchedule: EmployeeSchedule) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val layoutDirection = LocalLayoutDirection.current

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Tus turnos",
                        fontWeight = FontWeight.SemiBold
                    )
                },
                scrollBehavior = scrollBehavior
            )
        },
    ) { innerPadding ->
        EmployeeScheduleCalendar(
            contentPadding = PaddingValues(
                start = innerPadding.calculateStartPadding(layoutDirection) + 16.dp,
                end = innerPadding.calculateEndPadding(layoutDirection) + 16.dp,
            ),
            modifier = Modifier.padding(top = innerPadding.calculateTopPadding(), bottom = innerPadding.calculateBottomPadding()),
            schedule = employeeSchedule
        )
    }
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
                    type = ShiftType.GENERAL_DUTY
                ),
                date = LocalDate.parse("2025-06-20")
            ),
            ShiftDate(
                shift = Shift(
                    startTime = LocalTime.parse("14:00:00"),
                    endTime = LocalTime.parse("22:00:00"),
                    type = ShiftType.KITCHEN_LEAD
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