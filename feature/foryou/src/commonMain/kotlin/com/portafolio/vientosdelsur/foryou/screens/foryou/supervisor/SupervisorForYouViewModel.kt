package com.portafolio.vientosdelsur.foryou.screens.foryou.supervisor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f776.core.common.takeOrNull
import com.portafolio.vientosdelsur.domain.employee.EmployeeRepository
import kotlinx.coroutines.flow.*
import kotlin.time.Duration.Companion.seconds

internal class SupervisorForYouViewModel(private val employeeRepository: EmployeeRepository) : ViewModel() {
    private val _employees = flow { emit(employeeRepository.getEmployeesToday().takeOrNull()) }.filterNotNull()

    val employees = _employees.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5.seconds),
        emptyList()
    )

}
