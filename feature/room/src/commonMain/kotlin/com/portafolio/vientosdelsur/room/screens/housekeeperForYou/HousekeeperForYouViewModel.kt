@file:OptIn(ExperimentalCoroutinesApi::class)

package com.portafolio.vientosdelsur.room.screens.housekeeperForYou

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f776.core.common.takeOrNull
import com.portafolio.vientosdelsur.domain.room.RoomRepository
import com.portafolio.vientosdelsur.room.screens.housekeeperForYou.model.toRoomUi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration.Companion.seconds

internal class HousekeeperForYouViewModel(private val roomRepository: RoomRepository) : ViewModel() {

    private val _selectedDate =
        MutableStateFlow(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date)
    val selectedDate = _selectedDate.asStateFlow()

    fun onSelectDate(date: LocalDate) = _selectedDate.update { date }

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