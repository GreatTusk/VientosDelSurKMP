package com.portafolio.vientosdelsur.data.shift.network

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.f776.core.common.map
import com.f776.core.network.safeCall
import com.portafolio.vientosdelsur.network.BuildConfig
import com.portafolio.vientosdelsur.shared.dto.BaseResponseDto
import com.portafolio.vientosdelsur.shared.dto.room.MonthlyShiftDistributionDto
import com.portafolio.vientosdelsur.shared.dto.shift.ScheduleDto
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.datetime.LocalDate

internal class KtorRemoteShiftDataSource(private val httpClient: HttpClient) : RemoteShiftDataSource {
    override suspend fun getEmployeeSchedule(employeeId: Int): Result<ScheduleDto, DataError.Remote> =
        safeCall<BaseResponseDto<ScheduleDto>> {
            httpClient.get("${BuildConfig.BASE_URL}/shifts/employee/$employeeId")
        }.map { it.data }

    override suspend fun getAllEmployeeSchedule(date: LocalDate): Result<List<MonthlyShiftDistributionDto>, DataError.Remote> =
        safeCall<BaseResponseDto<List<MonthlyShiftDistributionDto>>> {
            httpClient.get("${BuildConfig.BASE_URL}/shifts/scheduling") {
                parameter("date", date)
            }
        }.map { it.data }
    
}