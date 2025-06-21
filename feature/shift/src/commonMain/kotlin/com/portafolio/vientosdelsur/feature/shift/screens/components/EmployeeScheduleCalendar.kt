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
import androidx.compose.ui.unit.sp
import com.f776.core.ui.theme.VientosDelSurTheme
import com.f776.japanesedictionary.core.resource.*
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.*
import com.portafolio.vientosdelsur.domain.shift.EmployeeSchedule
import com.portafolio.vientosdelsur.domain.shift.Shift
import com.portafolio.vientosdelsur.domain.shift.ShiftDate
import kotlinx.datetime.*
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

object ScheduleColors {
    val MORNING = Color(0xFFE8F5E8)     // Light Green
    val AFTERNOON = Color(0xFFFFF3E0)   // Light Orange
    val NIGHT = Color(0xFFE3F2FD)       // Light Blue
    val OVERTIME = Color(0xFFFCE4EC)    // Light Pink
    val WEEKEND = Color(0xFFF3E5F5)     // Light Purple
    val DAY_OFF = Color(0xFFEEEEEE)     // Light Gray
    val DEFAULT = Color(0xFFE1F5FE)     // Light Cyan
}

@Composable
fun EmployeeScheduleCalendar(
    schedule: EmployeeSchedule,
    modifier: Modifier = Modifier,
    currentMonth: YearMonth = YearMonth.now(),
    showLegend: Boolean = true,
    onDateClick: ((LocalDate, ShiftDate?) -> Unit)? = null
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
        schedule.workingDays.map { it.shift.type }.distinct().sorted()
    }

    val startMonth = remember { currentMonth.minusMonths(6) }
    val endMonth = remember { currentMonth.plusMonths(6) }
    val daysOfWeek = remember { daysOfWeek() }

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
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
            calendarScrollPaged = true
        )

        if (showLegend) {
            ScheduleLegend(
                shiftTypes = shiftTypes,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

@Composable
fun MonthNavigationHeader(
    currentMonth: YearMonth,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val monthName = remember(currentMonth) {
            Month(currentMonth.month.ordinal).name.lowercase().replaceFirstChar { it.titlecase() }
        }
        Text(
            text = "$monthName ${currentMonth.year}",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun DaysOfWeekHeader(daysOfWeek: List<DayOfWeek>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
    ) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray,
                text = stringResource(dayOfWeek.displayName)
            )
        }
    }
}

@Composable
fun ScheduleDay(
    day: CalendarDay,
    shiftDate: ShiftDate?,
    isDayOff: Boolean,
    onClick: (() -> Unit)? = null
) {
    val backgroundColor = when {
        isDayOff -> ScheduleColors.DAY_OFF
        shiftDate != null -> getShiftColor(shiftDate.shift)
        else -> Color.Transparent
    }

    val isToday = day.date == LocalDate.now()
    val isCurrentMonth = day.position == DayPosition.MonthDate

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
                if (onClick != null && isCurrentMonth) {
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
                fontSize = 14.sp,
                fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal,
                color = when {
                    !isCurrentMonth -> Color.Gray
                    isDayOff -> Color.Gray
                    else -> Color.Black
                }
            )

            // Shift indicator
            when {
                isDayOff -> {
                    Text(
                        text = "Libre",
                        fontSize = 8.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold
                    )
                }

                shiftDate != null -> {
                    Text(
                        text = formatShiftTime(shiftDate.shift),
                        fontSize = 7.sp,
                        color = Color.Black,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
fun ScheduleLegend(
    shiftTypes: List<String>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Schedule Legend",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Days off
            LegendItem(
                color = ScheduleColors.DAY_OFF,
                label = "Día libre",
                modifier = Modifier.padding(vertical = 2.dp)
            )

            // Shift types
            shiftTypes.forEach { shiftType ->
                LegendItem(
                    color = getShiftColorByType(shiftType),
                    label = shiftType.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase() else it.toString()
                    },
                    modifier = Modifier.padding(vertical = 2.dp)
                )
            }
        }
    }
}

@Composable
fun LegendItem(
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
                    color = Color.Gray.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(4.dp)
                )
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

// Helper functions
fun getShiftColor(shift: Shift): Color {
    return getShiftColorByType(shift.type)
}

fun getShiftColorByType(shiftType: String): Color {
    return when (shiftType.lowercase()) {
        "morning", "am", "day" -> ScheduleColors.MORNING
        "afternoon", "pm" -> ScheduleColors.AFTERNOON
        "night", "overnight", "graveyard" -> ScheduleColors.NIGHT
        "overtime", "ot" -> ScheduleColors.OVERTIME
        "weekend" -> ScheduleColors.WEEKEND
        else -> ScheduleColors.DEFAULT
    }
}

private val hourFormatter = LocalTime.Format {
    hour()
    chars(":")
    minute()
}

fun formatShiftTime(shift: Shift): String {
    val startFormatted = shift.startTime.format(hourFormatter)
    val endFormatted = shift.endTime.format(hourFormatter)
    return "$startFormatted-$endFormatted"
}

private val DayOfWeek.displayName: StringResource
    get() {
        return when (this) {
            DayOfWeek.MONDAY -> Res.string.monday
            DayOfWeek.TUESDAY -> Res.string.tuesday
            DayOfWeek.WEDNESDAY -> Res.string.wednesday
            DayOfWeek.THURSDAY -> Res.string.thursday
            DayOfWeek.FRIDAY -> Res.string.friday
            DayOfWeek.SATURDAY -> Res.string.saturday
            DayOfWeek.SUNDAY -> Res.string.sunday
            else -> error("Expected enums must have an else branch to be considered exhaustive")
        }
    }

@Preview
@Composable
fun EmployeeSchedulePreview() {
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
        Surface {
            EmployeeScheduleCalendar(
                schedule = mockSchedule,
                onDateClick = { _, _ ->
                }
            )
        }
    }
}