package com.f776.japanesedictionary.domain.imageanalysis

import com.f776.core.common.DataError
import com.f776.core.common.EmptyResult
import com.f776.core.common.Result

interface ImageAnalysisRepository {
    suspend fun getRoomsSubmittedToday(): Result<List<RoomAnalysis>, DataError.Remote>
    suspend fun reviewRoomCleaning(roomAnalysisId: Int, roomApprovalStatus: RoomApprovalStatus): EmptyResult<DataError.Remote>
}