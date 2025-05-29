package com.portafolio.vientosdelsur.feature.auth.screens.signup

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.f776.core.common.takeOrNull
import com.portafolio.vientosdelsur.domain.employee.EmployeeRepository
import com.portafolio.vientosdelsur.feature.auth.navigation.Registration
import kotlinx.coroutines.flow.*
import kotlin.time.Duration.Companion.seconds

internal class RegistrationFlowViewModel(
    savedStateHandle: SavedStateHandle,
    private val employeeRepository: EmployeeRepository
) : ViewModel() {
    private val userId = savedStateHandle.toRoute<Registration>().userId

    val employee = flow { emit(employeeRepository.getEmployee(userId).takeOrNull()) }
        .mapNotNull { it }
        .onEach {
            println(it)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(2.seconds),
            null
        )
}