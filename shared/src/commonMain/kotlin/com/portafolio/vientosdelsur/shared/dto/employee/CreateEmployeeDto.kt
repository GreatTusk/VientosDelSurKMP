package com.portafolio.vientosdelsur.shared.dto.employee

import kotlinx.serialization.Serializable

@Serializable
data class CreateEmployeeDto(
    val firstName: String
)
