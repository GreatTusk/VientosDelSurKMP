package com.portafolio.vientosdelsur.room.screens.housekeeperForYou

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f776.core.common.takeOrNull
import com.portafolio.vientosdelsur.domain.room.RoomCleaningStatus
import com.portafolio.vientosdelsur.domain.room.RoomCleaningType
import com.portafolio.vientosdelsur.domain.room.RoomRepository
import com.portafolio.vientosdelsur.domain.room.RoomState
import com.portafolio.vientosdelsur.room.screens.housekeeperForYou.model.toRoomUi
import kotlinx.coroutines.flow.*
import kotlin.time.Duration.Companion.seconds

internal class HousekeeperForYouViewModel(private val roomRepository: RoomRepository) : ViewModel() {
    private val rooms = flow { emit(roomRepository.getAllRooms()) }
        .map { it.takeOrNull() }
        .filterNotNull()
        .map { rooms ->
            rooms.map {
                RoomState(
                    room = it,
                    cleaningType = RoomCleaningType.ROOM,
                    cleaningStatus = RoomCleaningStatus.Pending
                ).toRoomUi()
            }
        }

    val uiState = rooms
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(2.seconds),
            emptyList()
        )
}