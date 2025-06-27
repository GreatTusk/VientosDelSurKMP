package com.portafolio.vientosdelsur.feature.shift.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.f776.core.ui.theme.VientosDelSurTheme
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.YearMonth
import com.kizitonwose.calendar.core.now
import com.portafolio.vientosdelsur.domain.shift.Schedule
import com.portafolio.vientosdelsur.domain.shift.Shift
import com.portafolio.vientosdelsur.domain.shift.ShiftDate
import com.portafolio.vientosdelsur.domain.shift.ShiftType
import com.portafolio.vientosdelsur.feature.shift.screens.employee.ui.displayName
import com.portafolio.vientosdelsur.feature.shift.screens.employee.ui.formatShiftTime
import com.portafolio.vientosdelsur.feature.shift.screens.employee.ui.getShiftColorByType
import com.portafolio.vientosdelsur.feature.shift.screens.employee.ui.hourFormatter
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.format
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun EmployeeScheduleCalendar(
    schedule: Schedule,
    modifier: Modifier = Modifier,
    currentMonth: YearMonth,
    onDateClick: ((LocalDate, ShiftDate?) -> Unit)? = null,
    contentPadding: PaddingValues
) {
    // Create maps for quick lookup
    val workingDaysMap = remember(schedule.workingDays) {
        schedule.workingDays.associateBy { it.date }
    }

    val daysOffSet = remember(schedule.daysOff) {
        schedule.daysOff.toSet()
    }

    // Get unique shift types for legend
    val shiftTypes = remember(schedule.workingDays) {
        schedule.workingDays.map { it.shift }.distinct()
    }

    val daysOfWeek = remember { DayOfWeek.entries }

    val state = rememberCalendarState(
        startMonth = currentMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first()
    )

    Column(modifier = modifier) {
        MonthNavigationHeader(currentMonth = state.firstVisibleMonth.yearMonth)

        DaysOfWeekHeader(daysOfWeek = daysOfWeek)

        HorizontalCalendar(
            state = state,
            dayContent = { day ->
                ScheduleDay(
                    day = day,
                    shiftDate = workingDaysMap[day.date],
                    isDayOff = daysOffSet.contains(day.date),
                    onClick = onDateClick?.let { callback ->
                        { callback(day.date, workingDaysMap[day.date]) }
                    }
                )
            },
            calendarScrollPaged = true,
            contentPadding = contentPadding
        )

        ScheduleLegend(
            shifts = shiftTypes,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Composable
internal fun MonthNavigationHeader(
    currentMonth: YearMonth,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val monthName = remember(currentMonth) { currentMonth.month.displayName }
        Text(
            text = "${stringResource(monthName)} ${currentMonth.year}",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
internal fun DaysOfWeekHeader(daysOfWeek: List<DayOfWeek>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                text = stringResource(dayOfWeek.displayName),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
internal fun ScheduleDay(
    day: CalendarDay,
    shiftDate: ShiftDate?,
    isDayOff: Boolean,
    onClick: (() -> Unit)? = null
) {
    val backgroundColor = when {
        isDayOff -> MaterialTheme.colorScheme.surfaceContainerHighest
        shiftDate != null -> getShiftColorByType(shiftDate.shift.type)
        else -> Color.Transparent
    }

    val isToday = day.date == LocalDate.now()
    val isCurrentWorkMonth = day.position == DayPosition.MonthDate || shiftDate != null

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(2.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .then(
                if (isToday) {
                    Modifier.border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(8.dp)
                    )
                } else Modifier
            )
            .then(
                if (onClick != null && isCurrentWorkMonth) {
                    Modifier.clickable { onClick() }
                } else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Day number
            Text(
                text = day.date.dayOfMonth.toString(),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal,
                color = when {
                    !isCurrentWorkMonth -> MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    isDayOff -> MaterialTheme.colorScheme.onSurfaceVariant
                    else -> MaterialTheme.colorScheme.onSurface
                }
            )

            // Shift indicator
            when {
                isDayOff -> {
                    Text(
                        text = "Libre",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium
                    )
                }

                shiftDate != null -> {
                    Text(
                        text = formatShiftTime(shiftDate.shift),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = MaterialTheme.typography.labelSmall.fontSize * 0.6f
                        ),
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
internal fun ScheduleLegend(
    shifts: List<Shift>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Turnos",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Days off
            LegendItem(
                color = MaterialTheme.colorScheme.surfaceContainerHighest,
                label = "Día libre",
                modifier = Modifier.padding(vertical = 2.dp)
            )

            // Shift types
            shifts.forEach { shift ->
                LegendItem(
                    color = getShiftColorByType(shift.type),
                    label = "${stringResource(shift.type.displayName)} (${shift.startTime.format(hourFormatter)} - ${shift.endTime.format(hourFormatter)})",
                    modifier = Modifier.padding(vertical = 2.dp)
                )
            }
        }
    }
}

@Composable
internal fun LegendItem(
    color: Color,
    label: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(color, RoundedCornerShape(4.dp))
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(4.dp)
                )
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}


@Preview
@Composable
private fun EmployeeSchedulePreview() {
    val mockSchedule = Schedule(
        workingDays = listOf(
            ShiftDate(
                shift = Shift(
                    startTime = LocalTime.parse("08:00:00"),
                    endTime = LocalTime.parse("16:00:00"),
                    type = ShiftType.KITCHEN_LEAD
                ),
                date = LocalDate.parse("2025-06-20")
            ),
            ShiftDate(
                shift = Shift(
                    startTime = LocalTime.parse("14:00:00"),
                    endTime = LocalTime.parse("22:00:00"),
                    type = ShiftType.GENERAL_DUTY
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
        Surface {
            EmployeeScheduleCalendar(
                schedule = mockSchedule,
                onDateClick = { _, _ ->
                },
                contentPadding = PaddingValues(horizontal = 16.dp),
                currentMonth = YearMonth.now()
            )
        }
    }
}