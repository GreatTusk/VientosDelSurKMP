package com.portafolio.vientosdelsur.shared.dto.employee

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

sealed interface EmployeeDto {
    val firstName: String
    val lastName: String
    val phoneNumber: String
    val dayOff: DayOfWeek
    val hireDate: LocalDateTime
    val occupation: EmployeeOccupationDto
    val housekeeperDto: HousekeeperDto?

    @Serializable
    data class Get(
        val id: Int,
        override val firstName: String,
        override val lastName: String,
        override val phoneNumber: String,
        override val dayOff: DayOfWeek,
        override val hireDate: LocalDateTime,
        override val occupation: EmployeeOccupationDto,
        override val housekeeperDto: HousekeeperDto?,
    ) : EmployeeDto

    @Serializable
    data class Create(
        val userDto: UserDto,
        override val firstName: String,
        override val lastName: String,
        override val phoneNumber: String,
        override val dayOff: DayOfWeek,
        override val hireDate: LocalDateTime,
        override val occupation: EmployeeOccupationDto,
        override val housekeeperDto: HousekeeperDto?,
    ) : EmployeeDto
}

@Serializable
enum class EmployeeOccupationDto {
    HousekeeperSupervisor,
    Housekeeper,
    Admin,
    Cook
}