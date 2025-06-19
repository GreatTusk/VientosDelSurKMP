package com.portafolio.vientosdelsur.feature.hotel.screens.roomanalysis.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.f776.core.ui.theme.VientosDelSurTheme
import com.f776.japanesedictionary.domain.imageanalysis.RoomAnalysis
import com.f776.japanesedictionary.domain.imageanalysis.ImageAnalysisResult
import com.f776.japanesedictionary.domain.imageanalysis.RoomApprovalStatus
import com.portafolio.vientosdelsur.domain.room.Room
import com.portafolio.vientosdelsur.domain.room.RoomType
import com.portafolio.vientosdelsur.feature.hotel.screens.roomanalysis.toStringRes
import kotlinx.datetime.*
import kotlinx.datetime.format.char
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun RoomAnalysisCard(
    modifier: Modifier = Modifier,
    roomAnalysis: RoomAnalysis,
    onImageSelected: () -> Unit
) {
    val formattedDateTime = remember { roomAnalysis.updatedAt.format(localeDateFormatter) }
    OutlinedCard(
        modifier = modifier,
        border = BorderStroke(0.dp, Color.Transparent),
        onClick = onImageSelected
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier.fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                AsyncImage(
                    model = roomAnalysis.imageUrl,
                    contentDescription = "Imagen de habitación ${roomAnalysis.room.roomNumber}",
                    contentScale = ContentScale.Crop
                )
                RevisionStateChip(
                    modifier = Modifier.align(Alignment.TopEnd).padding(end = 4.dp, top = 4.dp),
                    roomApprovalStatus = roomAnalysis.approvalStatus
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = stringResource(roomAnalysis.result.toStringRes()), style = MaterialTheme.typography.bodyLarge)

            Text(
                text = formattedDateTime,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )

            Text(
                text = "Habitación ${roomAnalysis.room.roomNumber}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

private val localeDateFormatter = LocalDateTime.Format {
    dayOfMonth()
    chars("/")
    monthNumber()
    char('/')
    year()
    char(' ')
    hour()
    char(':')
    minute()
}

@Preview
@Composable
private fun RoomAnalysisCardPreview() {
    val sampleRoomAnalysis = RoomAnalysis(
        id = 1,
        room = Room(
            id = 101,
            roomNumber = "A-101",
            roomType = RoomType.DOUBLE
        ),
        updatedAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
        result = ImageAnalysisResult.CLEAN, // assuming this enum exists
        imageUrl = "https://example.com/images/room101.jpg",
        approvalStatus = RoomApprovalStatus.APPROVED
    )
    VientosDelSurTheme {
        Surface {
            RoomAnalysisCard(
                roomAnalysis = sampleRoomAnalysis,
                onImageSelected = { }
            )
        }
    }
}