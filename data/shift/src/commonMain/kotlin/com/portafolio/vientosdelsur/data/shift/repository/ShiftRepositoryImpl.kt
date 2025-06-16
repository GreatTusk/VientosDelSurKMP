package com.portafolio.vientosdelsur.data.shift.repository

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.f776.core.common.map
import com.portafolio.vientosdelsur.data.shift.mapper.toDomain
import com.portafolio.vientosdelsur.data.shift.network.RemoteShiftDataSource
import com.portafolio.vientosdelsur.domain.shift.EmployeeSchedule
import com.portafolio.vientosdelsur.domain.shift.ShiftRepository

internal class ShiftRepositoryImpl(private val remoteShiftDataSource: RemoteShiftDataSource): ShiftRepository {
    override suspend fun getEmployeeSchedule(employeeId: Int): Result<EmployeeSchedule, DataError.Remote> {
        return remoteShiftDataSource.getEmployeeSchedule(employeeId)
            .map { it.toDomain() }
    }
}