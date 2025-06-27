@file:OptIn(ExperimentalCoroutinesApi::class)

package com.portafolio.vientosdelsur.feature.shift.screens.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f776.core.common.onSuccess
import com.f776.core.common.takeOrNull
import com.kizitonwose.calendar.core.minusMonths
import com.kizitonwose.calendar.core.plusMonths
import com.portafolio.vientosdelsur.domain.shift.EmployeeSchedule
import com.portafolio.vientosdelsur.domain.shift.ShiftRepository
import com.portafolio.vientosdelsur.domain.shift.ShiftSchedulingRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration.Companion.seconds

internal class ShiftReviewerViewModel(
    private val shiftRepository: ShiftRepository,
    private val shiftSchedulingRepository: ShiftSchedulingRepository
) : ViewModel() {
    private val initialDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

    private val _currentMonth = MutableStateFlow(initialDate)
    val currentMonth = _currentMonth.asStateFlow()

    private val _refreshTrigger = MutableStateFlow(0)

    private val _monthlyShifts = currentMonth.combine(_refreshTrigger) { date, _ -> date }
        .flatMapLatest { date ->
            flow { emit(shiftRepository.getAllEmployeeSchedule(date).takeOrNull()) }
        }.filterNotNull()

    val monthlyShifts = _monthlyShifts.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5.seconds),
        initialValue = emptyList()
    )

    private val _shiftsDraft = MutableStateFlow<List<EmployeeSchedule>?>(null)
    val shiftsDraft = _shiftsDraft.asStateFlow()

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

    private val _eventChannel = Channel<String>()
    val eventChannel = _eventChannel.receiveAsFlow()

    fun onPreviousMonth() {
        _currentMonth.update { it.minusMonths(1) }
    }

    fun onNextMonth() {
        _currentMonth.update { it.plusMonths(1) }
    }

    fun onGenerateDistribution() {
        viewModelScope.launch {
            shiftSchedulingRepository.generateDraft()
                .onSuccess { data ->
                    _shiftsDraft.update { data }
                    _eventChannel.send("Se ha generado una nueva distribución")
                }
        }
    }

    fun onSaveDistribution() {
        viewModelScope.launch {
            shiftSchedulingRepository.saveDraft()
                .onSuccess {
                    _refreshTrigger.update { it + 1 }
                    _eventChannel.send("Se ha guardado la distribución correctamente")
                }
        }
    }
}