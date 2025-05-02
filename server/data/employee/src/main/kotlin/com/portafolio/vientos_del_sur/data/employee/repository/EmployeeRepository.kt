package com.portafolio.vientos_del_sur.data.employee.repository

interface EmployeeRepository {
    suspend fun allEmployees()
}