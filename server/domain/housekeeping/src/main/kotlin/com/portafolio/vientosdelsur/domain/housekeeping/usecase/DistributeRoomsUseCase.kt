package com.portafolio.vientosdelsur.domain.housekeeping.usecase

import com.f776.core.common.*
import com.portafolio.vientosdelsur.domain.employee.Floor
import com.portafolio.vientosdelsur.domain.employee.Occupation
import com.portafolio.vientosdelsur.domain.housekeeping.HousekeeperShiftRepository
import com.portafolio.vientosdelsur.domain.housekeeping.model.HousekeeperShift
import com.portafolio.vientosdelsur.domain.room.RoomBookingRepository
import com.portafolio.vientosdelsur.domain.room.RoomRepository
import com.portafolio.vientosdelsur.domain.room.model.RoomBooking
import com.portafolio.vientosdelsur.domain.shift.dateUntil
import com.portafolio.vientosdelsur.domain.shift.workingDays
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.datetime.LocalDate

typealias MonthlyRoomDistribution = Map<LocalDate, Map<HousekeeperShift, Set<RoomBooking>>>

class DistributeRoomsUseCase(
    private val roomBookingRepository: RoomBookingRepository,
    private val roomRepository: RoomRepository,
    private val housekeeperShiftRepository: HousekeeperShiftRepository
) {
    suspend operator fun invoke(month: LocalDate): Result<MonthlyRoomDistribution, DataError.Remote> =
        coroutineScope {
            val days = month.workingDays
            val first = days.first()
            val last = days.last()

            val shifts = async {
                housekeeperShiftRepository.getMonthlyShifts(
                    startDate = first,
                    endDate = last,
                    occupation = Occupation.HOUSEKEEPER
                )
            }

            val allRooms = async {
                roomRepository.getAllRooms()
                    .flatMap { it.id to it }
                    .map { it.toMap() }
            }

            val rooms = days.toList()
                .associateWith { date ->
                    async {
                        roomBookingRepository.getBookedRoomsOn(date)
                            .flatMap { roomBookingId ->
                                RoomBooking(
                                    room = checkNotNull(
                                        allRooms.await().takeOrNull()?.get(roomBookingId.roomId)
                                    ) { "Impossible state: Room was not found" },
                                    workUnits = roomBookingId.workUnits,
                                    cleaningType = roomBookingId.cleaningType
                                )
                            }
                    }
                }
                .mapValues { (_, values) -> values.await().takeOrNull() ?: emptyError("No rooms to work with") }


            Result.Success(
                getRoomsMonthlyDistribution(
                    range = first..last,
                    rooms = rooms,
                    shifts = shifts.await().takeOrNull() ?: emptyError("No shifts to work with")
                )
            )
        }

    internal suspend fun getRoomsMonthlyDistribution(
        range: ClosedRange<LocalDate>,
        rooms: Map<LocalDate, List<RoomBooking>>,
        shifts: Map<LocalDate, List<HousekeeperShift>>
    ) = coroutineScope {
        range.start.dateUntil(range.endInclusive)
            .associateWith { date ->
                async {
                    val housekeeperShifts = shifts[date] ?: return@async null
                    val roomBookings = rooms[date]?.ifEmpty { null }?.toMutableSet() ?: return@async null

                    val housekeeperAssignments = housekeeperShifts.associateWith { mutableSetOf<RoomBooking>() }
                    val remainingQuotas = housekeeperShifts.associateWith { it.workMinutes }.toMutableMap()
                    val activeHousekeepers = housekeeperShifts.toMutableList()

                    while (roomBookings.isNotEmpty() && activeHousekeepers.isNotEmpty()) {
                        val iterator = activeHousekeepers.iterator()
                        while (iterator.hasNext() && roomBookings.isNotEmpty()) {
                            val housekeeper = iterator.next()
                            val remainingQuota = remainingQuotas[housekeeper] ?: 0

                            if (remainingQuota <= 0) {
                                iterator.remove()
                                continue
                            }

                            val room = findPreferredRoom(housekeeper.employee.preferredFloor, roomBookings)
                            roomBookings.remove(room)
                            housekeeperAssignments[housekeeper]?.add(room)
                            remainingQuotas[housekeeper] = remainingQuota - room.workUnits

                            if ((remainingQuotas[housekeeper] ?: 0) <= 0) {
                                iterator.remove()
                            }
                        }
                    }

                    housekeeperAssignments.mapValues { (_, rooms) -> rooms.toSet() }
                }
            }
            .mapValues { (_, deferred) -> deferred.await() }
            .filterValues { it != null }
            .mapValues { (_, value) -> value!! }
    }

    internal fun findPreferredRoom(preferredFloor: Floor?, availableRooms: Set<RoomBooking>): RoomBooking {
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