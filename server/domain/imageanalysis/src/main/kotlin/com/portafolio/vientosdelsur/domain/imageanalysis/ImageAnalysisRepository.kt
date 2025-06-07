package com.portafolio.vientosdelsur.domain.imageanalysis

import com.f776.core.common.DataError
import com.f776.core.common.Result

interface ImageAnalysisRepository {
    suspend fun analyze(imageBytes: ByteArray): Result<List<ImageAnalysisResult>, DataError.Remote>
}