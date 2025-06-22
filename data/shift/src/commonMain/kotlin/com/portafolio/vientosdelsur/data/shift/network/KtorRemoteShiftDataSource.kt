package com.portafolio.vientosdelsur.data.shift.network

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.f776.core.common.map
import com.f776.core.network.safeCall
import com.portafolio.vientosdelsur.network.BuildConfig
import com.portafolio.vientosdelsur.shared.dto.BaseResponseDto
import com.portafolio.vientosdelsur.shared.dto.shift.ScheduleDto
import io.ktor.client.*
import io.ktor.client.request.*

internal class KtorRemoteShiftDataSource(private val httpClient: HttpClient) : RemoteShiftDataSource {
    override suspend fun getEmployeeSchedule(employeeId: Int): Result<ScheduleDto, DataError.Remote> =
        safeCall<BaseResponseDto<ScheduleDto>> {
            httpClient.get("${BuildConfig.BASE_URL}/shifts/employee/$employeeId")
        }.map { it.data }

    override suspend fun getAllEmployeeSchedule(): Result<List<ScheduleDto>, DataError.Remote> {
        safeCall<BaseResponseDto<ScheduleDto>> {
            httpClient.get("${BuildConfig.BASE_URL}/shifts/employee")
        }.map { it.data }
    }
}