package com.portafolio.vientosdelsur.foryou.screens.foryou

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f776.core.common.takeOrNull
import com.portafolio.vientosdelsur.domain.employee.EmployeeRepository
import kotlinx.coroutines.flow.*
import kotlin.time.Duration.Companion.seconds

internal class ForYouViewModel(private val employeeRepository: EmployeeRepository): ViewModel() {
    private val _user = flow { emit(employeeRepository.getEmployee()) }
        .map { it.takeOrNull() }
        .filterNotNull()

    val user = _user.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(2.seconds),
        null
    )
}