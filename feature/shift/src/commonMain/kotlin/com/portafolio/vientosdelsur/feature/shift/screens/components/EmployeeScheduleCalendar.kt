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
import com.portafolio.vientosdelsur.domain.shift.ShiftType
import kotlinx.datetime.*
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import vientosdelsur.feature.shift.generated.resources.general_duty
import vientosdelsur.feature.shift.generated.resources.kitchen_assistant
import vientosdelsur.feature.shift.generated.resources.kitchen_lead

@Composable
fun EmployeeScheduleCalendar(
    schedule: EmployeeSchedule,
    modifier: Modifier = Modifier,
    currentMonth: YearMonth = YearMonth.now(),
    showLegend: Boolean = true,
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
        schedule.workingDays.map { it.shift.type }.distinct().sorted()
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
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val monthName = remember(currentMonth) { currentMonth.month.displayName }
        Text(
            text = "${stringResource(monthName)} ${currentMonth.year}",
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
            .padding(horizontal = 16.dp),
    ) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Medium,
                color = Color.Gray,
                text = stringResource(dayOfWeek.displayName),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
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
        isDayOff -> Color(0xFFEEEEEE)
        shiftDate != null -> getShiftColorByType(shiftDate.shift.type)
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
    shiftTypes: List<ShiftType>,
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
                text = "Turnos",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Days off
            LegendItem(
                color = Color(0xFFEEEEEE),
                label = "Día libre",
                modifier = Modifier.padding(vertical = 2.dp)
            )

            // Shift types
            shiftTypes.forEach { shiftType ->
                LegendItem(
                    color = getShiftColorByType(shiftType),
                    label = stringResource(shiftType.displayName),
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

@Composable
fun getShiftColorByType(shiftType: ShiftType): Color {
    return when (shiftType) {
        ShiftType.GENERAL_DUTY -> MaterialTheme.colorScheme.primaryContainer
        ShiftType.KITCHEN_ASSISTANT -> MaterialTheme.colorScheme.secondaryContainer
        ShiftType.KITCHEN_LEAD -> MaterialTheme.colorScheme.tertiaryContainer
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

private val Month.displayName: StringResource
    get() = when (this) {
        Month.JANUARY -> Res.string.january
        Month.FEBRUARY -> Res.string.february
        Month.MARCH -> Res.string.march
        Month.APRIL -> Res.string.april
        Month.MAY -> Res.string.may
        Month.JUNE -> Res.string.june
        Month.JULY -> Res.string.july
        Month.AUGUST -> Res.string.august
        Month.SEPTEMBER -> Res.string.september
        Month.OCTOBER -> Res.string.october
        Month.NOVEMBER -> Res.string.november
        Month.DECEMBER -> Res.string.december
        else -> error("Expected enums must have an else branch to be considered exhaustive")
    }

private val ShiftType.displayName: StringResource
    get() = when (this) {
        ShiftType.GENERAL_DUTY -> vientosdelsur.feature.shift.generated.resources.Res.string.general_duty
        ShiftType.KITCHEN_ASSISTANT -> vientosdelsur.feature.shift.generated.resources.Res.string.kitchen_assistant
        ShiftType.KITCHEN_LEAD -> vientosdelsur.feature.shift.generated.resources.Res.string.kitchen_lead
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
                contentPadding = PaddingValues(horizontal = 16.dp)
            )
        }
    }
}