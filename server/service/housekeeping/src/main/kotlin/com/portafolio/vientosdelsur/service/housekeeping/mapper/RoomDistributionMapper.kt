package com.portafolio.vientosdelsur.service.housekeeping.mapper

import com.portafolio.vientosdelsur.domain.housekeeping.usecase.MonthlyRoomDistribution
import com.portafolio.vientosdelsur.service.employee.mapper.toEmployeeDto
import com.portafolio.vientosdelsur.shared.dto.employee.EmployeeDto
import com.portafolio.vientosdelsur.shared.dto.room.MonthlyRoomDistributionDto
import com.portafolio.vientosdelsur.shared.dto.room.RoomDistributionDto

internal fun MonthlyRoomDistribution.toRoomDistributionDto(): MonthlyRoomDistributionDto {
    return mapValues { (_, housekeeperShiftsWithRooms) ->
        housekeeperShiftsWithRooms.map { (shift, roomBookings) ->
            RoomDistributionDto(
                employeeDto = shift.employee.toEmployeeDto() as EmployeeDto.Get.Housekeeper,
                rooms = roomBookings.map { it.room.toRoomDto() }.toSet()
            )
        }
    }
}