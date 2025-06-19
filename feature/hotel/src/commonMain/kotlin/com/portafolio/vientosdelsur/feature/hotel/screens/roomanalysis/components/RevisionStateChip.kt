package com.portafolio.vientosdelsur.feature.hotel.screens.roomanalysis.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.f776.core.ui.theme.VientosDelSurTheme
import com.f776.japanesedictionary.domain.imageanalysis.RoomApprovalStatus
import com.portafolio.vientosdelsur.feature.hotel.screens.roomanalysis.toChipState
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun RevisionStateChip(modifier: Modifier = Modifier, roomApprovalStatus: RoomApprovalStatus) {
    val approvalChipUi = roomApprovalStatus.toChipState()
    val contentColor = contentColorFor(approvalChipUi.containerColor)

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        color = approvalChipUi.containerColor,
        contentColor = contentColor
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 8.dp)) {
            Canvas(modifier = Modifier.size(4.dp)) {
                drawCircle(contentColor)
            }
            Text(
                text = stringResource(approvalChipUi.text),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Preview
@Composable
private fun RevisionStateChipApprovedPreview() {
    VientosDelSurTheme {
        RevisionStateChip(
            roomApprovalStatus = RoomApprovalStatus.APPROVED
        )
    }
}

@Preview
@Composable
private fun RevisionStateChipRejectedPreview() {
    VientosDelSurTheme {
        RevisionStateChip(
            roomApprovalStatus = RoomApprovalStatus.REJECTED
        )
    }
}

@Preview
@Composable
private fun RevisionStateChipPendingPreview() {
    VientosDelSurTheme {
        RevisionStateChip(
            roomApprovalStatus = RoomApprovalStatus.PENDING
        )
    }
}