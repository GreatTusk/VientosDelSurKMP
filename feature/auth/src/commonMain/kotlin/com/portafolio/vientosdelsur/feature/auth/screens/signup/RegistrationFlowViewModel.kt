package com.portafolio.vientosdelsur.feature.auth.screens.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.f776.core.common.takeOrNull
import com.portafolio.vientosdelsur.domain.employee.EmployeeRepository
import com.portafolio.vientosdelsur.feature.auth.navigation.Registration
import com.portafolio.vientosdelsur.feature.auth.screens.signup.navigation.RegistrationRoute
import kotlinx.coroutines.flow.*
import kotlin.time.Duration.Companion.seconds

private const val MINIMUM_PROGRESS = 0.25f

internal class RegistrationFlowViewModel(
    savedStateHandle: SavedStateHandle,
    private val employeeRepository: EmployeeRepository
) : ViewModel() {
    private val userId = savedStateHandle.toRoute<Registration>().userId

    var progress by mutableFloatStateOf(MINIMUM_PROGRESS)
        private set

    val employee = flow { emit(employeeRepository.getEmployee(userId).takeOrNull()) }
        .filterNotNull()
        .onEach { println(it) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(2.seconds),
            null
        )


    fun onNavigationEvent(route: RegistrationRoute) { progress = route.progress }
}