package com.portafolio.vientosdelsur.shared.dto.room

import com.portafolio.vientosdelsur.shared.dto.employee.EmployeeDto
import kotlinx.datetime.LocalDate

typealias MonthlyRoomDistributionDto = Map<LocalDate, Map<EmployeeDto, Set<RoomDto>>>
