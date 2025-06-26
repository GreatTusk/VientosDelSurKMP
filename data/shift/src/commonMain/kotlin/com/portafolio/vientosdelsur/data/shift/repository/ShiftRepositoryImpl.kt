package com.portafolio.vientosdelsur.data.shift.repository

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.f776.core.common.flatMap
import com.f776.core.common.map
import com.portafolio.vientosdelsur.data.employee.mapper.toEmployee
import com.portafolio.vientosdelsur.data.shift.mapper.toDomain
import com.portafolio.vientosdelsur.data.shift.network.RemoteShiftDataSource
import com.portafolio.vientosdelsur.domain.shift.EmployeeSchedule
import com.portafolio.vientosdelsur.domain.shift.Schedule
import com.portafolio.vientosdelsur.domain.shift.ShiftRepository
import kotlinx.datetime.LocalDate

internal class ShiftRepositoryImpl(private val remoteShiftDataSource: RemoteShiftDataSource) : ShiftRepository {
    override suspend fun getEmployeeSchedule(employeeId: Int): Result<Schedule, DataError.Remote> {
        return remoteShiftDataSource.getEmployeeSchedule(employeeId)
            .map { it.toDomain() }
    }

    override suspend fun getAllEmployeeSchedule(date: LocalDate): Result<List<EmployeeSchedule>, DataError.Remote> {
        return remoteShiftDataSource.getAllEmployeeSchedule(date = date)
            .flatMap {
                EmployeeSchedule(
                    employee = it.employee.toEmployee(),
                    schedule = it.scheduleDto.toDomain()
                )
            }
    }
}