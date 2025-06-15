package com.f776.japanesedictionary.data.imageanalysis.network

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.f776.core.common.map
import com.f776.core.network.safeCall
import com.portafolio.vientosdelsur.network.BuildConfig
import com.portafolio.vientosdelsur.shared.dto.BaseResponseDto
import com.portafolio.vientosdelsur.shared.dto.imageanalysis.ImageAnalysisDto
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.datetime.LocalDate
import org.koin.core.parameter.parametersOf

internal class KtorImageAnalysisDataSource(private val httpClient: HttpClient) : ImageAnalysisDataSource {
    override suspend fun getRoomsSubmittedOn(date: LocalDate): Result<List<ImageAnalysisDto>, DataError.Remote> =
        safeCall<BaseResponseDto<List<ImageAnalysisDto>>> {
            httpClient.get("${BuildConfig.BASE_URL}/image-analysis/taken-on") {
                parametersOf("date", date)
            }
        }.map { it.data }
}