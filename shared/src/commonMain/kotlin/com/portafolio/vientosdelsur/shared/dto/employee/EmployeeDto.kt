package com.portafolio.vientosdelsur.shared.dto.employee

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

sealed interface EmployeeDto {
    val firstName: String
    val lastName: String
    val phoneNumber: String
    val dayOff: DayOfWeek
    val hireDate: LocalDate
    val occupation: EmployeeOccupationDto

    @Serializable
    sealed interface Get : EmployeeDto {
        val id: Int
        val userId: String
        val email: String
        val photoUrl: String?
        val isEnabled: Boolean

        @Serializable
        data class Housekeeper(
            override val id: Int,
            val role: HousekeeperRoleDto?,
            val preferredFloor: Int?,
            override val firstName: String,
            override val lastName: String,
            override val phoneNumber: String,
            override val dayOff: DayOfWeek,
            override val hireDate: LocalDate,
            override val occupation: EmployeeOccupationDto,
            override val userId: String,
            override val email: String,
            override val photoUrl: String?,
            override val isEnabled: Boolean,
        ) : Get

        @Serializable
        data class StandardEmployee(
            override val id: Int,
            override val firstName: String,
            override val lastName: String,
            override val phoneNumber: String,
            override val dayOff: DayOfWeek,
            override val hireDate: LocalDate,
            override val occupation: EmployeeOccupationDto,
            override val userId: String,
            override val email: String,
            override val photoUrl: String?,
            override val isEnabled: Boolean,
        ) : Get
    }

    @Serializable
    sealed interface Create : EmployeeDto {
        val userDto: UserDto

        @Serializable
        data class Housekeeper(
            val housekeeperRoleDto: HousekeeperRoleDto,
            override val userDto: UserDto,
            override val firstName: String,
            override val lastName: String,
            override val phoneNumber: String,
            override val dayOff: DayOfWeek,
            override val hireDate: LocalDate,
            override val occupation: EmployeeOccupationDto,
        ) : Create

        @Serializable
        data class StandardEmployee(
            override val userDto: UserDto,
            override val firstName: String,
            override val lastName: String,
            override val phoneNumber: String,
            override val dayOff: DayOfWeek,
            override val hireDate: LocalDate,
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