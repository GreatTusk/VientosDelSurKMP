package com.portafolio.vientosdelsur.foryou.screens.housekeeper

import com.portafolio.vientosdelsur.domain.room.*

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
            room = Room(id = 1, roomType = RoomType.SINGLE, roomNumber = 101.toString()),
            cleaningType = RoomCleaningType.ROOM,
            cleaningStatus = RoomCleaningStatus.Pending
        ),
        RoomState(
            room = Room(id = 2, roomType = RoomType.DOUBLE, roomNumber = 102.toString()),
            cleaningType = RoomCleaningType.GUEST,
            cleaningStatus = RoomCleaningStatus.InCleaning(now)
        ),
        RoomState(
            room = Room(id = 3, roomType = RoomType.TRIPLE, roomNumber = 203.toString()),
            cleaningType = RoomCleaningType.ROOM,
            cleaningStatus = RoomCleaningStatus.InRevision(now)
        ),
        RoomState(
            room = Room(id = 4, roomType = RoomType.QUAD, roomNumber = 204.toString()),
            cleaningType = RoomCleaningType.ROOM,
            cleaningStatus = RoomCleaningStatus.Done(now)
        ),
        RoomState(
            room = Room(id = 5, roomType = RoomType.SINGLE, roomNumber = 305.toString()),
            cleaningType = RoomCleaningType.GUEST,
            cleaningStatus = RoomCleaningStatus.Pending
        ),
        RoomState(
            room = Room(id = 6, roomType = RoomType.DOUBLE, roomNumber = 306.toString()),
            cleaningType = RoomCleaningType.ROOM,
            cleaningStatus = RoomCleaningStatus.InCleaning(now)
        ),
        RoomState(
            room = Room(id = 7, roomType = RoomType.TRIPLE, roomNumber = 407.toString()),
            cleaningType = RoomCleaningType.GUEST,
            cleaningStatus = RoomCleaningStatus.Done(now)
        ),
        RoomState(
            room = Room(id = 8, roomType = RoomType.QUAD, roomNumber = 408.toString()),
            cleaningType = RoomCleaningType.GUEST,
            cleaningStatus = RoomCleaningStatus.InRevision(now)
        ),
    )

    // Function to get a specific room state by index
    fun getRoomStateByIndex(index: Int): RoomState {
        return sampleRoomStates[index % sampleRoomStates.size]
    }
}