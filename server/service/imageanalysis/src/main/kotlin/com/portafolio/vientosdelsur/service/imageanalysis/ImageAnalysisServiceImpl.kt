package com.portafolio.vientosdelsur.service.imageanalysis

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.f776.core.common.map
import com.portafolio.vientosdelsur.domain.imageanalysis.ImageAnalysisRepository
import com.portafolio.vientosdelsur.domain.imageanalysis.drawAnalysisConclusion
import com.portafolio.vientosdelsur.domain.imageanalysis.storage.ImageStorageRepository
import com.portafolio.vientosdelsur.domain.imageanalysis.storage.SaveImageAnalysis
import com.portafolio.vientosdelsur.service.imageanalysis.mapper.extractProbabilities
import com.portafolio.vientosdelsur.service.imageanalysis.mapper.toImageAnalysisResultDto
import com.portafolio.vientosdelsur.shared.dto.BaseResponseDto
import com.portafolio.vientosdelsur.shared.dto.imageanalysis.ImageAnalysisResultDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal class ImageAnalysisServiceImpl(
    private val imageAnalysisRepository: ImageAnalysisRepository,
    private val imageStorageRepository: ImageStorageRepository,
    private val coroutineScope: CoroutineScope
) : ImageAnalysisService {
    override suspend fun analyze(
        imageBytes: ByteArray,
        roomId: Int
    ): Result<BaseResponseDto<ImageAnalysisResultDto>, DataError.Remote> {
        return imageAnalysisRepository.analyze(imageBytes)
            .map { imageAnalysisResults ->
                coroutineScope.launch {
                    val (cleanProb, uncleanProb) = imageAnalysisResults.extractProbabilities()
                    imageStorageRepository.saveImageAnalysis(
                        SaveImageAnalysis(
                            roomId = roomId,
                            cleanProbability = cleanProb,
                            uncleanProbability = uncleanProb
                        ),
                        bytes = imageBytes
                    )
                }

                BaseResponseDto(
                    message = "Successful analysis",
                    data = imageAnalysisResults.drawAnalysisConclusion().toImageAnalysisResultDto()
                )
            }
    }

    override suspend fun serveImage(analysisId: Int): Result<ByteArray, DataError.Remote> {
        return imageStorageRepository.getImageById(analysisId)
    }
}