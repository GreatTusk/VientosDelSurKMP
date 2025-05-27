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

    @Serializable
    sealed interface Get : EmployeeDto {
        val id: Int

        @Serializable
        data class Housekeeper(
            override val id: Int,
            override val firstName: String,
            override val lastName: String,
            override val phoneNumber: String,
            override val dayOff: DayOfWeek,
            override val hireDate: LocalDateTime,
            override val occupation: EmployeeOccupationDto,
            val role: HousekeeperRoleDto?,
            val preferredFloor: Int?
        ) : Get

        @Serializable
        data class StandardEmployee(
            override val id: Int,
            override val firstName: String,
            override val lastName: String,
            override val phoneNumber: String,
            override val dayOff: DayOfWeek,
            override val hireDate: LocalDateTime,
            override val occupation: EmployeeOccupationDto,
        ) : Get
    }

    @Serializable
    sealed interface Create : EmployeeDto {
        val userDto: UserDto

        @Serializable
        data class Housekeeper(
            override val userDto: UserDto,
            override val firstName: String,
            override val lastName: String,
            override val phoneNumber: String,
            override val dayOff: DayOfWeek,
            override val hireDate: LocalDateTime,
            override val occupation: EmployeeOccupationDto,
            val housekeeperRoleDto: HousekeeperRoleDto
        ) : Create

        @Serializable
        data class StandardEmployee(
            override val userDto: UserDto,
            override val firstName: String,
            override val lastName: String,
            override val phoneNumber: String,
            override val dayOff: DayOfWeek,
            override val hireDate: LocalDateTime,
            override val occupation: EmployeeOccupationDto,
        ) : Create
    }
}

@Serializable
enum class EmployeeOccupationDto {
    HousekeeperSupervisor,
    Housekeeper,
    Admin,
    Cook
}