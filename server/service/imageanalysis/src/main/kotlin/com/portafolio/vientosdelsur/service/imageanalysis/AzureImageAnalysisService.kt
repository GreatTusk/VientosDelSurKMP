package com.portafolio.vientosdelsur.service.imageanalysis

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.f776.core.common.map
import com.portafolio.vientosdelsur.domain.imageanalysis.AnalysisConclusion
import com.portafolio.vientosdelsur.domain.imageanalysis.ImageAnalysisRepository
import com.portafolio.vientosdelsur.domain.imageanalysis.drawAnalysisConclusion
import com.portafolio.vientosdelsur.shared.dto.BaseResponseDto

internal class AzureImageAnalysisService(
    private val imageAnalysisRepository: ImageAnalysisRepository
) : ImageAnalysisService {
    override suspend fun analyze(
        imageBytes: ByteArray,
        roomId: Int
    ): Result<BaseResponseDto<AnalysisConclusion>, DataError.Remote> {
        return imageAnalysisRepository.analyze(imageBytes)
            .map { imageAnalysisResults ->
                BaseResponseDto(
                    message = "Successful analysis",
                    data = imageAnalysisResults.drawAnalysisConclusion()
                )
            }
    }
}