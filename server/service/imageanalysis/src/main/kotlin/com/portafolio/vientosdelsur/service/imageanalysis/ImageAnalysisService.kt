package com.portafolio.vientosdelsur.service.imageanalysis

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.shared.dto.imageanalysis.ImageAnalysisRequest
import com.portafolio.vientosdelsur.shared.dto.imageanalysis.ImageAnalysisResult

interface ImageAnalysisService {
    suspend fun analyze(analysisRequest: ImageAnalysisRequest): Result<ImageAnalysisResult, DataError.Remote>
}