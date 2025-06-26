@file:OptIn(ExperimentalCoroutinesApi::class)

package com.portafolio.vientosdelsur.feature.shift.screens.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f776.core.common.takeOrNull
import com.kizitonwose.calendar.core.minusMonths
import com.kizitonwose.calendar.core.plusMonths
import com.portafolio.vientosdelsur.domain.shift.ShiftRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration.Companion.seconds

internal class ShiftReviewerViewModel(private val shiftRepository: ShiftRepository) : ViewModel() {
    private val _currentMonth =
        MutableStateFlow(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date)

    val currentMonth = _currentMonth.asStateFlow()

    private val _monthlyShifts = currentMonth.flatMapLatest { date ->
        flow { emit(shiftRepository.getAllEmployeeSchedule(date).takeOrNull()) }
    }.filterNotNull()

    val monthlyShifts = _monthlyShifts.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5.seconds),
        initialValue = emptyList()
    )

    fun updateMonth(monthSelector: MonthSelector) {
        when (monthSelector) {
            MonthSelector.NEXT -> _currentMonth.update { it.plusMonths(1) }
            MonthSelector.PREVIOUS -> _currentMonth.update { it.minusMonths(1) }
        }
    }
}

internal enum class MonthSelector { NEXT, PREVIOUS }