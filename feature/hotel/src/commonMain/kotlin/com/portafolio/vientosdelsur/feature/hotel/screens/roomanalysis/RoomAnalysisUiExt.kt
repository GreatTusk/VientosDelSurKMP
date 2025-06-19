package com.portafolio.vientosdelsur.feature.hotel.screens.roomanalysis

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.f776.japanesedictionary.domain.imageanalysis.ImageAnalysisResult
import com.f776.japanesedictionary.domain.imageanalysis.RoomApprovalStatus
import com.portafolio.vientosdelsur.feature.hotel.screens.roomanalysis.model.RoomApprovalChipUi
import vientosdelsur.feature.hotel.generated.resources.*

internal fun ImageAnalysisResult.toStringRes() = when (this) {
    ImageAnalysisResult.CLEAN -> Res.string.room_status_clean_title
    ImageAnalysisResult.SLIGHTLY_DIRTY -> Res.string.room_status_slightly_dirty_title
    ImageAnalysisResult.MODERATELY_DIRTY ->Res.string.room_status_moderately_dirty_title
    ImageAnalysisResult.VERY_DIRTY -> Res.string.room_status_very_dirty_title
    ImageAnalysisResult.EXTREMELY_DIRTY -> Res.string.room_status_extremely_dirty_title
}

@Composable
internal fun RoomApprovalStatus.toChipState() = when (this) {
    RoomApprovalStatus.APPROVED -> RoomApprovalChipUi(
        text = Res.string.room_approved_state,
        containerColor = MaterialTheme.colorScheme.primaryContainer,
    )
    RoomApprovalStatus.PENDING -> RoomApprovalChipUi(
        text = Res.string.room_pending_state,
        containerColor = MaterialTheme.colorScheme.surfaceContainer
    )
    RoomApprovalStatus.REJECTED -> RoomApprovalChipUi(
        text = Res.string.room_rejected_state,
        containerColor = MaterialTheme.colorScheme.errorContainer
    )
}