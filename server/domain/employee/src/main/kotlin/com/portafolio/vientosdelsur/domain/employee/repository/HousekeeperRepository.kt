package com.portafolio.vientosdelsur.domain.employee.repository

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.domain.employee.Employee

interface HousekeeperRepository {
    suspend fun allHousekeepers(): Result<List<Employee.Housekeeper>, DataError.Remote>
    suspend fun getHousekeeperById(id: Int): Result<Employee.Housekeeper, DataError.Remote>
}