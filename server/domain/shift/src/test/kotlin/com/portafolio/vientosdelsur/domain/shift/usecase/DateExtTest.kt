package com.portafolio.vientosdelsur.domain.shift.usecase

import com.portafolio.vientosdelsur.domain.shift.*
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.time.DayOfWeek
import kotlin.test.Test
import kotlin.test.assertTrue

class DateExtTest {
    private val date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

    @Test
    fun `test obtaining fist sunday of current month`() {
        val firstMonday = date.firstSunday.also(::println)

        assertTrue("${firstMonday.dayOfWeek} is not Monday") {
            firstMonday.dayOfWeek == DayOfWeek.SUNDAY
        }
    }

    @Test
    fun `test obtaining last sunday of current month`() {
        val lastSunday = date.lastSunday

        assertTrue("${lastSunday.dayOfWeek} is not Monday") {
            lastSunday.dayOfWeek == DayOfWeek.SUNDAY
        }
    }

    @Test
    fun `iterate through a month`() {
        date.workingDays.forEach(::println)
    }

    @Test
    fun `iterate through sundays of a month`() {
        date.sundays.forEach(::println)
    }
}