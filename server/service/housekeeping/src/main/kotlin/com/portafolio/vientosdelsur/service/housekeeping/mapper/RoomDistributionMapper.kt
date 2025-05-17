package com.portafolio.vientosdelsur.service.housekeeping.mapper

import com.portafolio.vientosdelsur.domain.housekeeping.model.RoomBooking
import com.portafolio.vientosdelsur.domain.shift.model.HousekeeperShift
import com.portafolio.vientosdelsur.shared.dto.MonthlyRoomDistributionDto
import kotlinx.datetime.LocalDate


fun Map<LocalDate, Map<HousekeeperShift, Set<RoomBooking>>>.toRoomDistributionDto(): MonthlyRoomDistributionDto {
    return mapValues { (_, housekeeperShiftsWithRooms) ->
        housekeeperShiftsWithRooms.mapKeys { (shift, _) ->
            shift.employee.toEmployeeDto()
        }.mapValues { (_, roomBookings) ->
            roomBookings.map { it.room.toRoomDto() }.toSet()
        }
    }
}