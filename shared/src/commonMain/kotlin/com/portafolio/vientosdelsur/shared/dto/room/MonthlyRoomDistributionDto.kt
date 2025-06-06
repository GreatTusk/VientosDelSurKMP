package com.portafolio.vientosdelsur.shared.dto.room

import com.portafolio.vientosdelsur.shared.dto.employee.EmployeeDto
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

typealias MonthlyRoomDistributionDto = Map<LocalDate, List<RoomDistributionDto>>

@Serializable
data class RoomDistributionDto(
    val employeeDto: EmployeeDto.Get.Housekeeper,
    val rooms: Set<RoomDto>
)