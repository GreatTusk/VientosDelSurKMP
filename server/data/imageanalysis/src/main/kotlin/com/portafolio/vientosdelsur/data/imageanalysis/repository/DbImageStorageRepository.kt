package com.portafolio.vientosdelsur.data.imageanalysis.repository

import com.f776.core.common.DataError
import com.f776.core.common.EmptyResult
import com.f776.core.common.Result
import com.f776.core.common.emptyError
import com.portafolio.vientosdelsur.core.database.entity.imageanalysis.ImageAnalysisEntity
import com.portafolio.vientosdelsur.core.database.entity.imageanalysis.ImageAnalysisTable
import com.portafolio.vientosdelsur.core.database.entity.room.RoomEntity
import com.portafolio.vientosdelsur.core.database.util.safeSuspendTransaction
import com.portafolio.vientosdelsur.data.imageanalysis.mapper.toImageAnalysis
import com.portafolio.vientosdelsur.domain.imageanalysis.storage.ImageAnalysis
import com.portafolio.vientosdelsur.domain.imageanalysis.storage.ImageStorageRepository
import com.portafolio.vientosdelsur.domain.imageanalysis.storage.SaveImageAnalysis
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.kotlin.datetime.date
import org.jetbrains.exposed.sql.statements.api.ExposedBlob

internal object DbImageStorageRepository : ImageStorageRepository {
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

    override suspend fun getImageAnalysisTakenOn(date: LocalDate): Result<List<ImageAnalysis>, DataError.Remote> =
        safeSuspendTransaction {
            ImageAnalysisEntity.find { ImageAnalysisTable.uploadedAt.date() eq date }
                .map { it.toImageAnalysis() }
        }

    override suspend fun getImageAnalysisFromRoomOn(
        roomId: Int,
        date: LocalDate
    ): Result<List<ImageAnalysis>, DataError.Remote> = safeSuspendTransaction {
        ImageAnalysisEntity.find { (ImageAnalysisTable.uploadedAt.date() eq date) and (ImageAnalysisTable.roomId eq roomId) }
            .map { it.toImageAnalysis() }
    }

    override suspend fun getApprovedAnalysisOn(date: LocalDate): Result<List<ImageAnalysis>, DataError.Remote> =
        safeSuspendTransaction {
            ImageAnalysisEntity.find {
                (ImageAnalysisTable.uploadedAt.date() eq date) and (ImageAnalysisTable.cleanProbability greater ImageAnalysisTable.uncleanProbability)
            }.map { it.toImageAnalysis() }
        }

    override suspend fun getDisapprovedAnalysisOn(date: LocalDate): Result<List<ImageAnalysis>, DataError.Remote> =
        safeSuspendTransaction {
            ImageAnalysisEntity.find {
                (ImageAnalysisTable.uploadedAt.date() eq date) and (ImageAnalysisTable.cleanProbability lessEq ImageAnalysisTable.uncleanProbability)
            }.map { it.toImageAnalysis() }
        }

    override suspend fun getImageById(analysisId: Int): Result<ByteArray, DataError.Remote> = safeSuspendTransaction {
        val image = ImageAnalysisTable.select(ImageAnalysisTable.image)
            .where { ImageAnalysisTable.id eq analysisId }
            .firstOrNull() ?: emptyError("Analysis not found")

        image[ImageAnalysisTable.image].bytes
    }
}