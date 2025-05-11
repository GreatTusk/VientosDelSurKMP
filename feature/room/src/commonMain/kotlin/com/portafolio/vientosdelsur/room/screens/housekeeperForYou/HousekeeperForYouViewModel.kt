package com.portafolio.vientosdelsur.room.screens.housekeeperForYou

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f776.core.common.takeOrNull
import com.portafolio.vientosdelsur.domain.room.RoomRepository
import com.portafolio.vientosdelsur.room.screens.housekeeperForYou.model.toRoomUi
import com.portafolio.vientosdelsur.shared.domain.RoomCleaningStatus
import com.portafolio.vientosdelsur.shared.domain.RoomCleaningType
import com.portafolio.vientosdelsur.shared.domain.RoomState
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
                    roomCleaningType = RoomCleaningType.ROOM,
                    roomCleaningStatus = RoomCleaningStatus.Pending
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