package com.portafolio.vientosdelsur.foryou.screens.foryou

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.domain.employee.EmployeeRepository
import kotlinx.coroutines.flow.*
import kotlin.time.Duration.Companion.seconds

internal class ForYouViewModel(private val employeeRepository: EmployeeRepository) : ViewModel() {
    private val _user = flow { emit(employeeRepository.getEmployee()) }

    val user = _user.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(2.seconds),
        Result.Loading
    )
}