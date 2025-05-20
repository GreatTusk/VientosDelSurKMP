package com.portafolio.vientosdelsur.service.housekeeping.mapper

import com.portafolio.vientosdelsur.domain.housekeeping.usecase.MonthlyRoomDistribution
import com.portafolio.vientosdelsur.service.employee.mapper.toEmployeeDto
import com.portafolio.vientosdelsur.shared.dto.MonthlyRoomDistributionDto


fun MonthlyRoomDistribution.toRoomDistributionDto(): MonthlyRoomDistributionDto {
    return mapValues { (_, housekeeperShiftsWithRooms) ->
        housekeeperShiftsWithRooms.mapKeys { (shift, _) ->
            shift.employee.toEmployeeDto()
        }.mapValues { (_, roomBookings) ->
            roomBookings.map { it.room.toRoomDto() }.toSet()
        }
    }
}