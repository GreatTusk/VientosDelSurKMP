package com.portafolio.vientosdelsur.data.imageanalysis.repository

import com.f776.core.common.DataError
import com.f776.core.common.EmptyResult
import com.f776.core.common.Result
import com.f776.core.common.emptyError
import com.portafolio.vientosdelsur.core.database.entity.imageanalysis.ImageAnalysisEntity
import com.portafolio.vientosdelsur.core.database.entity.imageanalysis.ImageAnalysisTable
import com.portafolio.vientosdelsur.core.database.entity.room.RoomEntity
import com.portafolio.vientosdelsur.core.database.util.safeSuspendTransaction
import com.portafolio.vientosdelsur.domain.imageanalysis.storage.ImageAnalysis
import com.portafolio.vientosdelsur.domain.imageanalysis.storage.ImageStorageRepository
import com.portafolio.vientosdelsur.domain.imageanalysis.storage.SaveImageAnalysis
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.sql.statements.api.ExposedBlob

internal object DbImageStorageRepository: ImageStorageRepository {
    override suspend fun saveImageAnalysis(
        saveImageAnalysis: SaveImageAnalysis,
        bytes: ByteArray
    ): EmptyResult<DataError.Remote> = safeSuspendTransaction {
        ImageAnalysisEntity.new {
            room = RoomEntity.findById(saveImageAnalysis.roomId) ?: emptyError("Room not found")
            image = ExposedBlob(bytes)
            uploadedAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            cleanProbability = saveImageAnalysis.cleanProbability
            uncleanProbability = saveImageAnalysis.uncleanProbability
        }
    }

    override suspend fun getImageAnalysisTakenOn(date: LocalDate): Result<List<ImageAnalysis>, DataError.Remote> {
        TODO("Not yet implemented")
    }

    override suspend fun getImageAnalysisFromRoomOn(
        roomId: Int,
        date: LocalDate
    ): Result<List<ImageAnalysis>, DataError.Remote> {
        TODO("Not yet implemented")
    }

    override suspend fun getApprovedAnalysisOn(date: LocalDate): Result<List<ImageAnalysis>, DataError.Remote> {
        TODO("Not yet implemented")
    }

    override suspend fun getDisapprovedAnalysisOn(date: LocalDate): Result<List<ImageAnalysis>, DataError.Remote> {
        TODO("Not yet implemented")
    }
}