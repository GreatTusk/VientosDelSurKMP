package com.portafolio.vientosdelsur.feature.shift.screens.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f776.core.common.takeOrNull
import com.portafolio.vientosdelsur.domain.shift.ShiftRepository
import kotlinx.coroutines.flow.*
import kotlin.time.Duration.Companion.seconds

internal class ShiftReviewerViewModel(private val shiftRepository: ShiftRepository): ViewModel() {
    private val _monthlyShifts = flow { emit(shiftRepository.getAllEmployeeSchedule().takeOrNull()) }
        .filterNotNull()

    val monthlyShifts = _monthlyShifts.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5.seconds),
        initialValue = emptyList()
    )
}