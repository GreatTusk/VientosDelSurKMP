@file:OptIn(ExperimentalMaterial3Api::class)

package com.portafolio.vientosdelsur.feature.shift.screens.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.f776.core.ui.theme.VientosDelSurTheme
import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.domain.employee.Occupation
import com.portafolio.vientosdelsur.domain.shift.*
import com.portafolio.vientosdelsur.feature.shift.screens.components.EmployeeScheduleCalendar
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun ShiftReviewerScreenRoot(
    modifier: Modifier = Modifier,
    shiftReviewerViewModel: ShiftReviewerViewModel = koinViewModel()
) {
    val monthlyShifts by shiftReviewerViewModel.monthlyShifts.collectAsStateWithLifecycle()

    ShiftReviewerScreen(modifier = modifier, monthlyShifts = monthlyShifts)
}

@Composable
private fun ShiftReviewerScreen(modifier: Modifier = Modifier, monthlyShifts: List<EmployeeSchedule>) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val layoutDirection = LocalLayoutDirection.current

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Turnos del personal",
                        fontWeight = FontWeight.SemiBold
                    )
                },
                scrollBehavior = scrollBehavior
            )
        },
    ) { innerPadding ->
        LazyColumn(
            contentPadding = PaddingValues(
                top= innerPadding.calculateTopPadding(),
                bottom = innerPadding.calculateBottomPadding(),
                start = innerPadding.calculateStartPadding(layoutDirection) + 16.dp,
                end = innerPadding.calculateEndPadding(layoutDirection) + 16.dp,
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = monthlyShifts, key = { it.employee.id }) { employeeSchedule ->
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = employeeSchedule.employee.fullName,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    EmployeeScheduleCalendar(
                        contentPadding = PaddingValues(),
                        schedule = employeeSchedule.schedule
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ShiftReviewerScreenPreview() {
    VientosDelSurTheme {
        ShiftReviewerScreen(
            monthlyShifts = listOf(
                EmployeeSchedule(
                    employee = Employee(
                        id = 1,
                        firstName = "Pable",
                        lastName = "",
                        occupation = Occupation.SUPERVISOR,
                        userId = "123",
                        email = "",
                        photoUrl = null,
                        phoneNumber = "",
                        isEnabled = true
                    ),
                    schedule = Schedule(
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
                )
            )
        )
    }
}