package com.portafolio.vientosdelsur.service.imageanalysis

import com.f776.core.common.DataError
import com.f776.core.common.EmptyResult
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.shared.dto.BaseResponseDto
import com.portafolio.vientosdelsur.shared.dto.imageanalysis.ImageAnalysisDto
import com.portafolio.vientosdelsur.shared.dto.imageanalysis.ImageAnalysisResultDto
import com.portafolio.vientosdelsur.shared.dto.imageanalysis.RoomAnalysisStatusDto
import com.portafolio.vientosdelsur.shared.dto.imageanalysis.RoomCleaningReviewDto
import kotlinx.datetime.LocalDate

typealias ImageAnalysisResponse = BaseResponseDto<List<ImageAnalysisDto>>

interface ImageAnalysisService {
    suspend fun analyze(
        imageBytes: ByteArray,
        roomId: Int,
        housekeeperId: Int
    ): Result<BaseResponseDto<ImageAnalysisResultDto>, DataError.Remote>

    suspend fun serveImage(analysisId: Int): Result<ByteArray, DataError.Remote>
    suspend fun getImageAnalysisTakenOn(date: LocalDate): Result<ImageAnalysisResponse, DataError.Remote>
    suspend fun getImageAnalysisFromRoomOn(
        roomId: Int,
        date: LocalDate
    ): Result<ImageAnalysisResponse, DataError.Remote>

    suspend fun getApprovedAnalysisOn(date: LocalDate): Result<ImageAnalysisResponse, DataError.Remote>
    suspend fun getDisapprovedAnalysisOn(date: LocalDate): Result<ImageAnalysisResponse, DataError.Remote>
    suspend fun updateRoomCleaningStatus(roomCleaningReviewDto: RoomCleaningReviewDto): EmptyResult<DataError.Remote>
}