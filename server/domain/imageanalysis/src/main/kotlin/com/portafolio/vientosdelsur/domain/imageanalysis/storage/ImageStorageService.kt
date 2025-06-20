package com.portafolio.vientosdelsur.domain.imageanalysis.storage

import com.f776.core.common.Result
import com.f776.core.common.DataError
import com.f776.core.common.EmptyResult
import com.portafolio.vientosdelsur.domain.imageanalysis.RoomAnalysisState
import kotlinx.datetime.LocalDate

interface ImageStorageRepository {
    suspend fun saveImageAnalysis(saveImageAnalysis: SaveImageAnalysis, bytes: ByteArray): EmptyResult<DataError.Remote>
    suspend fun getImageAnalysisTakenOn(date: LocalDate): Result<List<ImageAnalysis>, DataError.Remote>
    suspend fun getImageAnalysisFromRoomOn(roomId: Int, date: LocalDate): Result<List<ImageAnalysis>, DataError.Remote>
    suspend fun getApprovedAnalysisOn(date: LocalDate): Result<List<ImageAnalysis>, DataError.Remote>
    suspend fun getDisapprovedAnalysisOn(date: LocalDate): Result<List<ImageAnalysis>, DataError.Remote>
    suspend fun getImageById(analysisId: Int): Result<ByteArray, DataError.Remote>
    suspend fun updateRoomCleaningStatus(analysisId: Int, roomAnalysisState: RoomAnalysisState): EmptyResult<DataError.Remote>
}