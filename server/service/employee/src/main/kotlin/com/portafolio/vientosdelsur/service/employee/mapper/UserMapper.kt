package com.portafolio.vientosdelsur.service.employee.mapper

import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.domain.user.User
import com.portafolio.vientosdelsur.shared.dto.employee.UserDto

fun UserDto.toUser(employee: Employee) = User(
    id = userId,
    email = email,
    name = "${employee.data.firstName} ${employee.data.lastName}",
    photoUrl = photoUrl,
    phoneNumber = employee.data.phoneNumber,
    isEnabled = isEnabled,
    createdAt = employee.userData.createdAt,
    updatedAt = employee.userData.updatedAt
)