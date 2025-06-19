package com.f776.japanesedictionary.data.imageanalysis.repository

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.f776.core.common.flatMap
import com.f776.japanesedictionary.data.imageanalysis.mapper.toImageAnalysis
import com.f776.japanesedictionary.data.imageanalysis.network.ImageAnalysisDataSource
import com.f776.japanesedictionary.domain.imageanalysis.RoomAnalysis
import com.f776.japanesedictionary.domain.imageanalysis.ImageAnalysisRepository
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

internal class ImageAnalysisRepositoryImpl(
    private val imageAnalysisDataSource: ImageAnalysisDataSource
) : ImageAnalysisRepository {
    override suspend fun getRoomsSubmittedToday(): Result<List<RoomAnalysis>, DataError.Remote> {
        return imageAnalysisDataSource.getRoomsSubmittedOn(
            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        )
            .flatMap { it.toImageAnalysis() }
    }
}