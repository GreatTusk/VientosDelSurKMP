package com.portafolio.vientosdelsur.data.shift.repository

import com.f776.core.common.*
import com.portafolio.vientosdelsur.data.employee.mapper.toEmployee
import com.portafolio.vientosdelsur.data.shift.mapper.toDomain
import com.portafolio.vientosdelsur.data.shift.network.RemoteShiftDataSource
import com.portafolio.vientosdelsur.domain.shift.EmployeeSchedule
import com.portafolio.vientosdelsur.domain.shift.ShiftSchedulingRepository
import com.portafolio.vientosdelsur.shared.dto.room.MonthlyShiftDistributionDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

internal class ShiftSchedulingRepositoryImpl(
    private val remoteShiftDataSource: RemoteShiftDataSource
) : ShiftSchedulingRepository {

    private val _generatedDraft = MutableStateFlow<List<MonthlyShiftDistributionDto>?>(null)

    override suspend fun generateDraft(): Result<List<EmployeeSchedule>, DataError.Remote> {
        return remoteShiftDataSource.generateShiftScheduleDraft()
            .onSuccess { data -> _generatedDraft.update { data } }
            .flatMap { monthlyShiftDistributionDto ->
                EmployeeSchedule(
                    employee = monthlyShiftDistributionDto.employee.toEmployee(),
                    schedule = monthlyShiftDistributionDto.scheduleDto.toDomain()
                )
            }
    }

    override suspend fun saveDraft(shiftSchedule: List<EmployeeSchedule>): EmptyResult<DataError.Remote> {
        val draftToSave = _generatedDraft.value
        if (draftToSave != null) {
            return remoteShiftDataSource.saveShiftScheduleDraft(draftToSave)
        }
        return Result.Error(DataError.Remote.NOT_FOUND)
    }
}