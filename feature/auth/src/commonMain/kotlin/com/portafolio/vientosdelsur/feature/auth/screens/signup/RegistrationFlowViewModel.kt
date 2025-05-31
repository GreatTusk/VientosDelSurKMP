package com.portafolio.vientosdelsur.feature.auth.screens.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

    var progress by mutableFloatStateOf(RegistrationSteps.WEIGHT)
        private set

    val employee = flow { emit(employeeRepository.getEmployee(userId).takeOrNull()) }
        .filterNotNull()
        .onEach { println(it) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(2.seconds),
            null
        )


    fun onContinue() {
        progress = progress.plus(RegistrationSteps.WEIGHT).coerceAtMost(1f)
    }

    fun onGoBack() {
        progress = progress.minus(RegistrationSteps.WEIGHT).coerceAtLeast(RegistrationSteps.WEIGHT)
    }
}

enum class RegistrationSteps {
    PROFILE,
    EMPLOYEE_STATUS,
    ROLE,
    ADDITIONAL_INFO;

    companion object {
        val WEIGHT = 1f / entries.size
    }
}