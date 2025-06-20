package com.f776.japanesedictionary.domain.imageanalysis

import com.f776.core.common.DataError
import com.f776.core.common.Result

interface ImageAnalysisService {
    suspend fun classifyImage(byteArray: ByteArray, roomId: Int, housekeeperId: Int): Result<ImageAnalysisResult, DataError.Remote>
}