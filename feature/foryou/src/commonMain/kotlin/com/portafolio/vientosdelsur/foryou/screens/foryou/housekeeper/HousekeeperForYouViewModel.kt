@file:OptIn(ExperimentalCoroutinesApi::class)

package com.portafolio.vientosdelsur.foryou.screens.foryou.housekeeper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f776.core.common.takeOrNull
import com.portafolio.vientosdelsur.domain.auth.AuthService
import com.portafolio.vientosdelsur.domain.auth.UserRepository
import com.portafolio.vientosdelsur.domain.employee.EmployeeRepository
import com.portafolio.vientosdelsur.domain.room.RoomRepository
import com.portafolio.vientosdelsur.foryou.screens.foryou.housekeeper.model.toRoomUi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration.Companion.seconds

internal class HousekeeperForYouViewModel(
    private val roomRepository: RoomRepository,
    private val employeeId: Int,
    private val authService: AuthService,
) : ViewModel() {

    private val _selectedDate =
        MutableStateFlow(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date)
    val selectedDate = _selectedDate.asStateFlow()

    fun onSelectDate(date: LocalDate) = _selectedDate.update { date }

    private val rooms = _selectedDate
        .flatMapLatest { date -> flow { emit(roomRepository.getRoomDistributionForHousekeeperOn(employeeId, date)) } }
        .mapNotNull { it.takeOrNull() }
        .map { rooms -> rooms.map { it.toRoomUi() } }

    val uiState = rooms
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(2.seconds),
            emptyList()
        )

    fun onLogout() {
        viewModelScope.launch {
            authService.logout()
        }
    }
}