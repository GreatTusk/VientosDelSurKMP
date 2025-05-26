package com.portafolio.vientosdelsur.data.employee.network

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.f776.core.common.map
import com.f776.core.network.safeCall
import com.portafolio.vientosdelsur.network.BuildConfig
import com.portafolio.vientosdelsur.shared.dto.BaseResponseDto
import com.portafolio.vientosdelsur.shared.dto.employee.EmployeeDto
import io.ktor.client.*
import io.ktor.client.request.*

internal class KtorRemoteEmployeeDataSource(private val httpClient: HttpClient) : RemoteEmployeeDataSource {
    override suspend fun getEmployeeByUserId(userId: String): Result<EmployeeDto.Get, DataError.Remote> =
        safeCall<BaseResponseDto<EmployeeDto.Get>> {
            httpClient.get("${BuildConfig.BASE_URL}/employee") {
                parameter("user-id", userId)
            }
        }.map { it.data }

    override suspend fun getAllEmployees(): Result<List<EmployeeDto.Get>, DataError.Remote> =
        safeCall<BaseResponseDto<List<EmployeeDto.Get>>> {
            httpClient.get("${BuildConfig.BASE_URL}/employee")
        }.map { it.data }
}