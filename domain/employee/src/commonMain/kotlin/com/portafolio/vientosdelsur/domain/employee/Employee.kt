package com.portafolio.vientosdelsur.domain.employee

data class Employee(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val role: EmployeeRole
)
