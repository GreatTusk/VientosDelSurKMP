@file:OptIn(ExperimentalCoroutinesApi::class)

package com.portafolio.vientosdelsur.feature.shift.screens.employee

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f776.core.common.takeOrNull
import com.portafolio.vientosdelsur.domain.auth.UserRepository
import com.portafolio.vientosdelsur.domain.shift.Schedule
import com.portafolio.vientosdelsur.domain.shift.ShiftRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlin.time.Duration.Companion.seconds

internal class ShiftViewModel(
    private val shiftRepository: ShiftRepository,
    userRepository: UserRepository
) : ViewModel() {
    private val _employeeSchedule = userRepository.currentEmployee.flatMapLatest { employee ->
        flow {
            employee?.id?.let {
                emit(
                    shiftRepository.getEmployeeSchedule(it).takeOrNull()
                )
            }
        }
    }.filterNotNull()


    val schedule = _employeeSchedule.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5.seconds),
        initialValue = Schedule(emptyList(), emptyList())
    )
}