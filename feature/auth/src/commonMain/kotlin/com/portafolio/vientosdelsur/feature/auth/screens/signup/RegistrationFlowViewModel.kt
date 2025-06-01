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
import com.portafolio.vientosdelsur.feature.auth.screens.signup.navigation.RegistrationRoute
import kotlinx.coroutines.flow.*
import kotlinx.datetime.*
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

    fun onNavigationEvent(route: RegistrationRoute) {
        progress = route.progress
    }

    var dayOfWeek by mutableStateOf<DayOfWeek?>(null)
        private set

    fun onDayOfWeekSelected(selectedDay: DayOfWeek?) {
        dayOfWeek = if (dayOfWeek == selectedDay) null else selectedDay
    }

    var hireDate by mutableStateOf<LocalDate?>(null)
        private set

    fun onHireDateSelected(millis: Long?) {
        if (millis == null) {
            hireDate = null
            return
        }

        val instant = Instant.fromEpochMilliseconds(millis)
        val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault()).date
        hireDate = localDateTime
    }

    var dayOff by mutableStateOf<DayOfWeek?>(null)
        private set

    fun onDayOffSelected(dayOfWeek: DayOfWeek) {
        dayOff = if (dayOff == dayOfWeek) null else dayOfWeek
    }
}

internal val LocalDate.formatted: String
    get() {
        val day = dayOfMonth.toString().padStart(2, '0')
        val month = monthNumber.toString().padStart(2, '0')
        val year = year
        return "$day/$month/$year"
    }
