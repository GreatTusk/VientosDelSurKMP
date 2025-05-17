package com.portafolio.vientosdelsur.domain.housekeeping.usecase

import com.f776.core.common.*
import com.portafolio.vientosdelsur.domain.employee.Floor
import com.portafolio.vientosdelsur.domain.employee.Occupation
import com.portafolio.vientosdelsur.domain.housekeeping.RoomBookingRepository
import com.portafolio.vientosdelsur.domain.housekeeping.model.RoomBooking
import com.portafolio.vientosdelsur.domain.shift.ShiftRepository
import com.portafolio.vientosdelsur.domain.shift.dateUntil
import com.portafolio.vientosdelsur.domain.shift.model.HousekeeperShift
import com.portafolio.vientosdelsur.domain.shift.workingDays
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.datetime.LocalDate

class DistributeRoomsUseCase(
    private val roomBookingRepository: RoomBookingRepository,
    private val shiftRepository: ShiftRepository
) {
    suspend operator fun invoke(month: LocalDate): Result<Map<LocalDate, Map<HousekeeperShift, Set<RoomBooking>>>, DataError.Remote> =
        coroutineScope {
            val days = month.workingDays
            val first = days.first()
            val last = days.last()
            val rooms = days.toList().associateWith {
                async {
                    roomBookingRepository.getBookedRoomsOn(it)
                }
            }.mapValues { (_, values) -> values.await().takeOrNull() ?: emptyError("No rooms to work with") }

            val shifts = async {
                shiftRepository.getMonthlyShifts(
                    startDate = first,
                    endDate = last,
                    occupation = Occupation.HOUSEKEEPER
                )
            }

            Result.Success(
                getRoomsMonthlyDistribution(
                    range = first..last,
                    rooms = rooms,
                    shifts = shifts.await().takeOrNull() ?: emptyError("No shifts to work with")
                )
            )
        }

    private suspend fun getRoomsMonthlyDistribution(
        range: ClosedRange<LocalDate>,
        rooms: Map<LocalDate, List<RoomBooking>>,
        shifts: Map<LocalDate, List<HousekeeperShift>>
    ) = coroutineScope {
        return@coroutineScope range.start.dateUntil(range.endInclusive)
            .associateWith { date ->
                async {
                    val housekeeperShifts = shifts[date] ?: return@async null
                    val roomBookings = rooms[date]?.ifEmpty { null }?.toMutableSet() ?: return@async null

                    housekeeperShifts.associateWith { housekeeper ->
                        var remainingQuota = housekeeper.workMinutes
                        val housekeeperRooms = mutableSetOf<RoomBooking>()

                        while (remainingQuota >= 0 && roomBookings.isNotEmpty()) {
                            val room = findPreferredRoom(housekeeper.employee.preferredFloor, roomBookings)
                            roomBookings.remove(room)
                            housekeeperRooms.add(room)
                            remainingQuota -= room.workUnits
                        }

                        housekeeperRooms.toSet()
                    }
                }
            }.mapValues { (_, deferred) -> deferred.await() }
            .filterValues { it != null }
            .mapValues { (_, value) -> value!! }
    }

    // Remember to leave null preferred floors last and order by work unit desc
    private fun findPreferredRoom(preferredFloor: Floor?, availableRooms: Set<RoomBooking>): RoomBooking {
        if (preferredFloor == null) {
            return availableRooms.random()
        }
        return availableRooms.find { it.room.floor.number == preferredFloor.floor } ?: if (preferredFloor.floor <= 2) {
            availableRooms.minBy { it.room.floor.number }
        } else {
            availableRooms.maxBy { it.room.floor.number }
        }
    }
}