package com.portafolio.vientosdelsur.domain.shift

import kotlinx.datetime.*
import java.time.temporal.IsoFields

fun LocalDate.dateUntil(endDate: LocalDate, daysStep: Int = 1): Sequence<LocalDate> {
    val startDate = this
    return generateSequence(startDate) { date ->
        val next = date.plus(daysStep, DateTimeUnit.DAY)
        if (next <= endDate) next else null
    }
}

val LocalDate.firstSunday: LocalDate
    get() {
        val firstDayOfMonth = LocalDate(year, monthNumber, 1)

        val daysToAdd = if (firstDayOfMonth.dayOfWeek == DayOfWeek.SUNDAY) {
            0
        } else {
            val daysAfterMonday = (firstDayOfMonth.dayOfWeek.ordinal - DayOfWeek.SUNDAY.ordinal + 7) % 7
            (7 - daysAfterMonday) % 7
        }

        return firstDayOfMonth.plus(daysToAdd, DateTimeUnit.DAY)
    }

val LocalDate.lastSunday: LocalDate
    get() {
        val nextMonth = if (monthNumber == 12) {
            LocalDate(year + 1, 1, 1)
        } else {
            LocalDate(year, monthNumber + 1, 1)
        }

        val lastDay = nextMonth.minus(1, DateTimeUnit.DAY)

        val daysToSubtract = if (lastDay.dayOfWeek == DayOfWeek.SUNDAY) {
            0
        } else {
            (lastDay.dayOfWeek.ordinal - DayOfWeek.SUNDAY.ordinal + 7) % 7
        }

        return lastDay.minus(daysToSubtract, DateTimeUnit.DAY)
    }

val LocalDate.sundays: List<LocalDate>
    get() = firstSunday.dateUntil(lastSunday, daysStep = 7).toList()

val LocalDate.workingDays: Sequence<LocalDate>
    get() = firstSunday.minus(6, DateTimeUnit.DAY).dateUntil(lastSunday)

val LocalDate.workingDaysRange: ClosedRange<LocalDate>
    get() = firstSunday.minus(6, DateTimeUnit.DAY)..lastSunday

fun LocalDate.isSameWeekAs(other: LocalDate): Boolean {
    return this.toJavaLocalDate().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) == other.toJavaLocalDate().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR)
}