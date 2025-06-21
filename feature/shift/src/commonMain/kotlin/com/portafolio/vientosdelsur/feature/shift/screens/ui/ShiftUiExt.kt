package com.portafolio.vientosdelsur.feature.shift.screens.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.f776.japanesedictionary.core.resource.*
import com.portafolio.vientosdelsur.domain.shift.Shift
import com.portafolio.vientosdelsur.domain.shift.ShiftType
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month
import kotlinx.datetime.format
import org.jetbrains.compose.resources.StringResource
import vientosdelsur.feature.shift.generated.resources.general_duty
import vientosdelsur.feature.shift.generated.resources.kitchen_assistant
import vientosdelsur.feature.shift.generated.resources.kitchen_lead

@Composable
internal fun getShiftColorByType(shiftType: ShiftType): Color {
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

internal fun formatShiftTime(shift: Shift): String {
    val startFormatted = shift.startTime.format(hourFormatter)
    val endFormatted = shift.endTime.format(hourFormatter)
    return "$startFormatted-$endFormatted"
}

internal val DayOfWeek.displayName: StringResource
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

internal val Month.displayName: StringResource
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

internal val ShiftType.displayName: StringResource
    get() = when (this) {
        ShiftType.GENERAL_DUTY -> vientosdelsur.feature.shift.generated.resources.Res.string.general_duty
        ShiftType.KITCHEN_ASSISTANT -> vientosdelsur.feature.shift.generated.resources.Res.string.kitchen_assistant
        ShiftType.KITCHEN_LEAD -> vientosdelsur.feature.shift.generated.resources.Res.string.kitchen_lead
    }