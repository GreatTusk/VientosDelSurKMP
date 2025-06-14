package com.portafolio.vientosdelsur.service.imageanalysis

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.shared.dto.BaseResponseDto
import com.portafolio.vientosdelsur.shared.dto.imageanalysis.ImageAnalysisResultDto

interface ImageAnalysisService {
    suspend fun analyze(imageBytes: ByteArray, roomId: Int): Result<BaseResponseDto<ImageAnalysisResultDto>, DataError.Remote>
    suspend fun serveImage(analysisId: Int): Result<ByteArray, DataError.Remote>
}