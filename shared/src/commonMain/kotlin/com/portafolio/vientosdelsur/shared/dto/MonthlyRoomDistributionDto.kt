package com.portafolio.vientosdelsur.shared.dto

import kotlinx.datetime.LocalDate

typealias MonthlyRoomDistributionDto = Map<LocalDate, Map<EmployeeDto, Set<RoomDto>>>
