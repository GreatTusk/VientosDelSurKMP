package com.f776.japanesedictionary.domain.imageanalysis

import com.f776.core.common.DataError
import com.f776.core.common.Result

interface ImageAnalysisService {
    suspend fun classifyImage(byteArray: ByteArray): Result<ImageAnalysisResult, DataError.Remote>
}