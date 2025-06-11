package com.portafolio.vientosdelsur.data.employee.network

import com.f776.core.common.DataError
import com.f776.core.common.EmptyResult
import com.f776.core.common.Result
import com.f776.core.common.map
import com.f776.core.network.safeCall
import com.portafolio.vientosdelsur.network.BuildConfig
import com.portafolio.vientosdelsur.shared.dto.BaseResponseDto
import com.portafolio.vientosdelsur.shared.dto.employee.EmployeeDto
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.datetime.LocalDate
import kotlinx.serialization.json.Json

internal class KtorRemoteEmployeeDataSource(private val httpClient: HttpClient) : RemoteEmployeeDataSource {
    override suspend fun getEmployeeByUserId(userId: String): Result<EmployeeDto.Get, DataError.Remote> =
        safeCall<BaseResponseDto<EmployeeDto.Get>> {
            httpClient.get("${BuildConfig.BASE_URL}/user/$userId")
        }.map { it.data }

    override suspend fun getAllEmployees(): Result<List<EmployeeDto.Get>, DataError.Remote> =
        safeCall<BaseResponseDto<List<EmployeeDto.Get>>> {
            httpClient.get("${BuildConfig.BASE_URL}/employee")
        }.map { it.data }

    override suspend fun getEmployeesToday(today: LocalDate): Result<List<EmployeeDto.Get>, DataError.Remote> =
        safeCall<BaseResponseDto<List<EmployeeDto.Get>>> {
            httpClient.get("${BuildConfig.BASE_URL}/employee") {
                parameter("date", today)
            }
        }.map { it.data }

    override suspend fun isUserActive(userId: String): EmptyResult<DataError.Remote> = safeCall<Unit> {
        httpClient.get("${BuildConfig.BASE_URL}/employee/active/$userId")
    }

    override suspend fun createEmployee(
        employeeDto: EmployeeDto.Create,
        profilePhoto: ByteArray?
    ): EmptyResult<DataError.Remote> = safeCall<Unit> {
        httpClient.post("${BuildConfig.BASE_URL}/employee") {
            setBody(
                MultiPartFormDataContent(
                    formData {
                        append("data", Json.encodeToString(employeeDto))
                        profilePhoto?.let {
                            append(
                                key = "profile-photo",
                                value = it,
                                headers = Headers.build {
                                    append(HttpHeaders.ContentType, "image/png")
                                    append(HttpHeaders.ContentDisposition, "filename=\"profile-photo.png\"")
                                }
                            )
                        }
                    }
                )
            )
        }
    }

}