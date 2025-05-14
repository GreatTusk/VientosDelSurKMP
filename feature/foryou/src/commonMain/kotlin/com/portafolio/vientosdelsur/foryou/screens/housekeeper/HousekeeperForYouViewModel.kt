@file:OptIn(ExperimentalCoroutinesApi::class)

package com.portafolio.vientosdelsur.foryou.screens.housekeeper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f776.core.common.takeOrNull
import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.domain.employee.EmployeeRole
import com.portafolio.vientosdelsur.domain.room.RoomRepository
import com.portafolio.vientosdelsur.foryou.screens.housekeeper.model.toRoomUi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration.Companion.seconds

internal class HousekeeperForYouViewModel(
    private val roomRepository: RoomRepository,
//    private val employeeRepository: EmployeeRepository
) : ViewModel() {

    private val _selectedDate =
        MutableStateFlow(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date)
    val selectedDate = _selectedDate.asStateFlow()

    fun onSelectDate(date: LocalDate) = _selectedDate.update { date }

    val user = flow {
        emit(
            Employee(
                id = 1,
                firstName = "Flor",
                lastName = "Muñoz",
                role = EmployeeRole.HOUSEKEEPER
            )
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(2.seconds),
        null
    )

    private val rooms = _selectedDate.flatMapLatest { date -> flow { emit(roomRepository.getAllRoomsState(date)) } }
        .map { it.takeOrNull() }
        .filterNotNull()
        .map { rooms -> rooms.map { it.toRoomUi() } }

    val uiState = rooms
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(2.seconds),
            emptyList()
        )
}