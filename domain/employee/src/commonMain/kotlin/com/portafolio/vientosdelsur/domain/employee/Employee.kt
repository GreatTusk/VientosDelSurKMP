package com.portafolio.vientosdelsur.domain.employee

data class Employee(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val occupation: Occupation,
    val userId: String,
    val email: String,
    val photoUrl: String?,
    val phoneNumber: String,
    val isEnabled: Boolean
)