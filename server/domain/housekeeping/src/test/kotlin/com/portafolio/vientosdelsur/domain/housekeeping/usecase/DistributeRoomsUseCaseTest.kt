package com.portafolio.vientosdelsur.domain.housekeeping.usecase

import com.f776.core.common.Result
import com.f776.core.common.takeOrNull
import com.portafolio.vientosdelsur.data.FakeHousekeeperShiftRepository
import com.portafolio.vientosdelsur.data.FakeRoomBookingRepository
import com.portafolio.vientosdelsur.data.FakeRoomRepository
import com.portafolio.vientosdelsur.domain.room.model.Floor
import com.portafolio.vientosdelsur.domain.room.model.RoomBooking
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlin.test.*

class DistributeRoomsUseCaseTest {
    private lateinit var useCase: DistributeRoomsUseCase
    private val fakeRoomBookingRepository = FakeRoomBookingRepository()
    private val fakeRoomRepository = FakeRoomRepository()
    private val fakeHousekeeperShiftRepository = FakeHousekeeperShiftRepository()

    @BeforeTest
    fun setUp() {
        useCase = DistributeRoomsUseCase(
            roomBookingRepository = fakeRoomBookingRepository,
            roomRepository = fakeRoomRepository,
            housekeeperShiftRepository = fakeHousekeeperShiftRepository
        )
    }

    @Test
    fun `invoke returns success and non-empty distribution`() = runBlocking {
        val testMonth = LocalDate(2023, Month.MAY, 1)
        val result = useCase.invoke(testMonth)
        assertTrue(result is Result.Success)
        val distribution = result.data
        assertTrue(distribution.isNotEmpty(), "Distribution should not be empty")
        // Check at least one day has assignments
        assertTrue(distribution.values.any { it.isNotEmpty() }, "At least one day should have assignments")
    }

    @Test
    fun `getRoomsMonthlyDistribution assigns rooms within quotas`() = runBlocking {
        val testMonth = LocalDate(2023, Month.MAY, 1)
        val result = useCase.invoke(testMonth)
        assertTrue(result is Result.Success)
        val distribution = result.data
        // For each day, each housekeeper should not exceed their workMinutes
        distribution.forEach { (_, assignments) ->
            assignments.forEach { (shift, rooms) ->
                val totalWork = rooms.sumOf { it.workUnits }
                assertTrue(totalWork <= shift.workMinutes, "Assigned work should not exceed quota")
            }
        }
    }

    @Test
    fun `findPreferredRoom returns room on preferred floor if available`() {
        val rooms = setOf(
            RoomBooking(
                room = fakeRoomRepository.getAllRoomsBlocking()[0],
                workUnits = 10,
                cleaningType = com.portafolio.vientosdelsur.domain.room.model.RoomCleaningType.ROOM
            ),
            RoomBooking(
                room = fakeRoomRepository.getAllRoomsBlocking()[1],
                workUnits = 10,
                cleaningType = com.portafolio.vientosdelsur.domain.room.model.RoomCleaningType.ROOM
            )
        )
        val preferredFloor = when(rooms.first().room.floor){
            Floor.ONE -> com.portafolio.vientosdelsur.domain.employee.Floor(1)
            Floor.TWO -> com.portafolio.vientosdelsur.domain.employee.Floor(2)
            Floor.THREE -> com.portafolio.vientosdelsur.domain.employee.Floor(3)
            Floor.FOUR -> com.portafolio.vientosdelsur.domain.employee.Floor(4)
        }
        val found = useCase.findPreferredRoom(preferredFloor, rooms)
        assertEquals(preferredFloor.floor, found.room.floor.number)
    }

    @Test
    fun `findPreferredRoom returns random room if no preferred floor`() {
        val rooms = setOf(
            RoomBooking(
                room = fakeRoomRepository.getAllRoomsBlocking()[0],
                workUnits = 10,
                cleaningType = com.portafolio.vientosdelsur.domain.room.model.RoomCleaningType.ROOM
            )
        )
        val found = useCase.findPreferredRoom(null, rooms)
        assertTrue(rooms.contains(found))
    }
}

// Helper to get all rooms synchronously for test
private fun FakeRoomRepository.getAllRoomsBlocking() =
    runBlocking { getAllRooms().takeOrNull() ?: emptyList() }

