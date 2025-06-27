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
    private val initialDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

    private val _currentMonth = MutableStateFlow(initialDate)
    val currentMonth = _currentMonth.asStateFlow()

    private val _monthlyShifts = currentMonth.flatMapLatest { date ->
        flow { emit(shiftRepository.getAllEmployeeSchedule(date).takeOrNull()) }
    }.filterNotNull()

    val monthlyShifts = _monthlyShifts.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5.seconds),
        initialValue = emptyList()
    )

    private val _shiftsDraft = flow { emit(shiftRepository) }

    val shiftsDraft = _shiftsDraft.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5.seconds),
        initialValue = emptyList()
    )

    val canGoBack = _currentMonth.map { it.month != initialDate.month }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5.seconds),
            initialValue = false
        )

    val canGoForward = _currentMonth.map { it.month != initialDate.plusMonths(1).month }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5.seconds),
            initialValue = true
        )

    fun onPreviousMonth() {
        _currentMonth.update { it.minusMonths(1) }
    }

    fun onNextMonth() {
        _currentMonth.update { it.plusMonths(1) }
    }

    fun onGenerateDistribution() {

    }

    fun onSaveDistribution() {

    }
}