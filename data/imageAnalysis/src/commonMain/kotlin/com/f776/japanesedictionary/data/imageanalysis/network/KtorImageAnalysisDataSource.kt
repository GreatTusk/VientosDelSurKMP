package com.f776.japanesedictionary.data.imageanalysis.network

import com.f776.core.common.DataError
import com.f776.core.common.EmptyResult
import com.f776.core.common.Result
import com.f776.core.common.map
import com.f776.core.network.safeCall
import com.f776.japanesedictionary.data.imageanalysis.mapper.toRoomAnalysisStatusDto
import com.f776.japanesedictionary.domain.imageanalysis.RoomApprovalStatus
import com.portafolio.vientosdelsur.network.BuildConfig
import com.portafolio.vientosdelsur.shared.dto.BaseResponseDto
import com.portafolio.vientosdelsur.shared.dto.imageanalysis.ImageAnalysisDto
import com.portafolio.vientosdelsur.shared.dto.imageanalysis.RoomCleaningReviewDto
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.datetime.LocalDate

internal class KtorImageAnalysisDataSource(private val httpClient: HttpClient) : ImageAnalysisDataSource {
    override suspend fun getRoomsSubmittedOn(date: LocalDate): Result<List<ImageAnalysisDto>, DataError.Remote> =
        safeCall<BaseResponseDto<List<ImageAnalysisDto>>> {
            httpClient.get("${BuildConfig.BASE_URL}/image-analysis/taken-on") {
                parameter("date", date)
            }
        }.map { it.data }

    override suspend fun reviewRoomCleaning(
        roomAnalysisId: Int,
        roomApprovalStatus: RoomApprovalStatus
    ): EmptyResult<DataError.Remote> = safeCall<Unit> {
        httpClient.post("${BuildConfig.BASE_URL}/image-analysis/review") {
            setBody(
                RoomCleaningReviewDto(
                    roomAnalysisId = roomAnalysisId,
                    roomAnalysisStatusDto = roomApprovalStatus.toRoomAnalysisStatusDto()
                )
            )
        }
    }
}