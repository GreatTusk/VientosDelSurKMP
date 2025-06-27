@file:OptIn(ExperimentalMaterial3Api::class)

package com.portafolio.vientosdelsur.feature.shift.screens.admin

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.f776.core.ui.components.HintMessage
import com.f776.core.ui.components.ObserveAsEvents
import com.f776.core.ui.theme.VientosDelSurTheme
import com.kizitonwose.calendar.core.YearMonth
import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.domain.employee.Occupation
import com.portafolio.vientosdelsur.domain.shift.*
import com.portafolio.vientosdelsur.feature.shift.screens.components.EmployeeScheduleCalendar
import com.portafolio.vientosdelsur.feature.shift.screens.employee.ui.displayName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun ShiftReviewerScreenRoot(
    modifier: Modifier = Modifier,
    shiftReviewerViewModel: ShiftReviewerViewModel = koinViewModel()
) {
    val monthlyShifts by shiftReviewerViewModel.monthlyShifts.collectAsStateWithLifecycle()
    val currentMonth by shiftReviewerViewModel.currentMonth.collectAsStateWithLifecycle()
    val canGoBack by shiftReviewerViewModel.canGoBack.collectAsStateWithLifecycle()
    val canGoForward by shiftReviewerViewModel.canGoForward.collectAsStateWithLifecycle()
    val scheduleDraft by shiftReviewerViewModel.shiftsDraft.collectAsStateWithLifecycle()

    ShiftReviewerScreen(
        modifier = modifier,
        monthlyShifts = monthlyShifts,
        currentMonth = currentMonth,
        onPreviousMonth = shiftReviewerViewModel::onPreviousMonth,
        onNextMonth = shiftReviewerViewModel::onNextMonth,
        canGoBack = canGoBack,
        canGoForward = canGoForward,
        scheduleDraft = scheduleDraft,
        onGenerateDraft = shiftReviewerViewModel::onGenerateDistribution,
        onSaveDraft = shiftReviewerViewModel::onSaveDistribution,
        events = shiftReviewerViewModel.eventChannel
    )
}

@Composable
private fun ShiftReviewerScreen(
    modifier: Modifier = Modifier,
    monthlyShifts: List<EmployeeSchedule>,
    currentMonth: LocalDate,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
    canGoBack: Boolean,
    canGoForward: Boolean,
    scheduleDraft: List<EmployeeSchedule>?,
    onGenerateDraft: () -> Unit,
    onSaveDraft: () -> Unit,
    events: Flow<String>
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val layoutDirection = LocalLayoutDirection.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    ObserveAsEvents(events) {
        scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar(it)
        }
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.padding(start = 12.dp)
                    ) {
                        Icon(imageVector = Icons.Default.CalendarMonth, contentDescription = null)
                        Text(text = stringResource(currentMonth.month.displayName).substring(0..2))
                    }
                },
                actions = {
                    IconButton(onClick = onPreviousMonth, enabled = canGoBack) {
                        Icon(imageVector = Icons.Default.ChevronLeft, contentDescription = null)
                    }
                    IconButton(onClick = onNextMonth, enabled = canGoForward) {
                        Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null)
                    }
                },
                title = {
                    Text(
                        text = "Turnos del personal",
                        fontWeight = FontWeight.SemiBold
                    )
                },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            AnimatedVisibility(visible = canGoBack) {
                Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    FloatingActionButton(onClick = onSaveDraft) {
                        Icon(imageVector = Icons.Default.Save, contentDescription = null)
                    }
                    ExtendedFloatingActionButton(
                        text = {
                            Text("Generar")
                        },
                        icon = {
                            Icon(imageVector = Icons.Default.Brush, contentDescription = null)
                        },
                        onClick = onGenerateDraft
                    )
                }
            }
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { innerPadding ->
        val shouldShowScheduleGrid = when {
            !canGoForward && monthlyShifts.isNotEmpty() -> true
            !canGoForward -> scheduleDraft != null
            !canGoBack -> monthlyShifts.isNotEmpty()
            else -> monthlyShifts.isNotEmpty()
        }

        AnimatedContent(shouldShowScheduleGrid) { showGrid ->
            if (showGrid) {
                val dataToShow = when {
                    !canGoForward && monthlyShifts.isNotEmpty() -> monthlyShifts
                    !canGoForward && scheduleDraft != null -> scheduleDraft
                    else -> monthlyShifts
                }

                LazyVerticalGrid(
                    contentPadding = PaddingValues(
                        top = innerPadding.calculateTopPadding(),
                        bottom = innerPadding.calculateBottomPadding() + 16.dp,
                        start = innerPadding.calculateStartPadding(layoutDirection) + 16.dp,
                        end = innerPadding.calculateEndPadding(layoutDirection) + 16.dp,
                    ),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    columns = GridCells.Adaptive(400.dp)
                ) {
                    itemsIndexed(
                        items = dataToShow,
                        key = { _, employee -> "${employee.employee.id}$currentMonth" }) { i, employeeSchedule ->
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.animateItem()) {
                            Text(
                                text = employeeSchedule.employee.fullName,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )

                            EmployeeScheduleCalendar(
                                contentPadding = PaddingValues(),
                                schedule = employeeSchedule.schedule,
                                currentMonth = YearMonth(year = currentMonth.year, month = currentMonth.month)
                            )
                            if (i != dataToShow.lastIndex) {
                                HorizontalDivider(modifier = Modifier.padding(8.dp))
                            }
                        }
                    }
                }
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    HintMessage(
                        modifier = Modifier.padding(24.dp),
                        icon = Icons.Default.SearchOff,
                        title = "No se ha generado una distribución para el mes de ${stringResource(currentMonth.month.displayName)}.",
                        subtitle = "Puede generar un borrador."
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
            ),
            currentMonth = LocalDate.parse("2025-06-22"),
            onPreviousMonth = {},
            onNextMonth = {},
            canGoBack = false,
            canGoForward = true,
            scheduleDraft = null,
            onGenerateDraft = {},
            onSaveDraft = {},
            events = emptyFlow()
        )
    }
}