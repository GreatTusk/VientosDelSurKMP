package com.portafolio.vientosdelsur.service.imageanalysis

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.f776.core.common.map
import com.portafolio.vientosdelsur.domain.imageanalysis.ImageAnalysisRepository
import com.portafolio.vientosdelsur.service.imageanalysis.mapper.toImageAnalysisResultDto
import com.portafolio.vientosdelsur.shared.dto.BaseResponseDto
import com.portafolio.vientosdelsur.shared.dto.imageanalysis.ImageAnalysisResultDto

internal class AzureImageAnalysisService(
    private val imageAnalysisRepository: ImageAnalysisRepository
) : ImageAnalysisService {
    override suspend fun analyze(
        imageBytes: ByteArray,
        roomId: Int
    ): Result<BaseResponseDto<Set<ImageAnalysisResultDto>>, DataError.Remote> {
        return imageAnalysisRepository.analyze(imageBytes)
            .map { imageAnalysisResults ->
                BaseResponseDto(
                    message = "Successful analysis",
                    data = imageAnalysisResults.map { it.toImageAnalysisResultDto() }.toSet()
                )
            }
    }
}