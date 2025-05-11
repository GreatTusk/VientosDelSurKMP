package com.portafolio.vientosdelsur.room.screens.housekeeperForYou

import com.portafolio.vientosdelsur.domain.room.*
import com.portafolio.vientosdelsur.shared.domain.RoomCleaningStatus
import com.portafolio.vientosdelsur.shared.domain.RoomCleaningType
import com.portafolio.vientosdelsur.shared.domain.RoomState
import com.portafolio.vientosdelsur.shared.domain.RoomType
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

/**
 * Static sample data for room states used in previews and testing
 */
object SampleRoomStates {
    private val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

    val sampleRoomStates = listOf(
        RoomState(
            room = Room(id = 1, roomType = RoomType.SINGLE, number = 101),
            roomCleaningType = RoomCleaningType.ROOM,
            roomCleaningStatus = RoomCleaningStatus.Pending
        ),
        RoomState(
            room = Room(id = 2, roomType = RoomType.DOUBLE, number = 102),
            roomCleaningType = RoomCleaningType.GUEST,
            roomCleaningStatus = RoomCleaningStatus.InCleaning(now)
        ),
        RoomState(
            room = Room(id = 3, roomType = RoomType.TRIPLE, number = 203),
            roomCleaningType = RoomCleaningType.ROOM,
            roomCleaningStatus = RoomCleaningStatus.InRevision(now)
        ),
        RoomState(
            room = Room(id = 4, roomType = RoomType.QUAD, number = 204),
            roomCleaningType = RoomCleaningType.OUT_OF_ORDER,
            roomCleaningStatus = RoomCleaningStatus.Done(now)
        ),
        RoomState(
            room = Room(id = 5, roomType = RoomType.SINGLE, number = 305),
            roomCleaningType = RoomCleaningType.GUEST,
            roomCleaningStatus = RoomCleaningStatus.Pending
        ),
        RoomState(
            room = Room(id = 6, roomType = RoomType.DOUBLE, number = 306),
            roomCleaningType = RoomCleaningType.ROOM,
            roomCleaningStatus = RoomCleaningStatus.InCleaning(now)
        ),
        RoomState(
            room = Room(id = 7, roomType = RoomType.TRIPLE, number = 407),
            roomCleaningType = RoomCleaningType.OUT_OF_ORDER,
            roomCleaningStatus = RoomCleaningStatus.Done(now)
        ),
        RoomState(
            room = Room(id = 8, roomType = RoomType.QUAD, number = 408),
            roomCleaningType = RoomCleaningType.GUEST,
            roomCleaningStatus = RoomCleaningStatus.InRevision(now)
        ),
    )

    // Function to get a specific room state by index
    fun getRoomStateByIndex(index: Int): RoomState {
        return sampleRoomStates[index % sampleRoomStates.size]
    }
}