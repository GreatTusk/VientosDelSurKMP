package com.f776.japanesedictionary.data.imageanalysis.network

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.shared.dto.imageanalysis.ImageAnalysisDto
import kotlinx.datetime.LocalDate

interface ImageAnalysisDataSource {
    suspend fun getRoomsSubmittedOn(date: LocalDate): Result<List<ImageAnalysisDto>, DataError.Remote>
}