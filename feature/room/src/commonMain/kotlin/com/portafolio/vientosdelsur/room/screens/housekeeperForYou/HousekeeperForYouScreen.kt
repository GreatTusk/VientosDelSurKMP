@file:OptIn(ExperimentalMaterial3Api::class)

package com.portafolio.vientosdelsur.room.screens.housekeeperForYou

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.twotone.Stairs
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.f776.core.ui.theme.VientosDelSurTheme
import com.portafolio.vientosdelsur.domain.room.RoomCleaningStatus
import com.portafolio.vientosdelsur.domain.room.RoomCleaningType
import com.portafolio.vientosdelsur.domain.room.RoomState
import com.portafolio.vientosdelsur.room.screens.housekeeperForYou.model.RoomStateUi
import com.portafolio.vientosdelsur.room.screens.housekeeperForYou.model.toRoomUi
import kotlinx.datetime.LocalTime
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeFormat
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import vientosdelsur.feature.room.generated.resources.Res
import vientosdelsur.feature.room.generated.resources.cleaning_state
import vientosdelsur.feature.room.generated.resources.cleaning_type


@Composable
internal fun RoomScreenRoot(
    modifier: Modifier = Modifier,
    housekeeperForYouViewModel: HousekeeperForYouViewModel = koinInject()
) {
    RoomScreen()
}

@Composable
private fun RoomScreen(modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(200.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(SampleRoomStates.sampleRoomStates.size) { index ->
            RoomStateCard(SampleRoomStates.getRoomStateByIndex(index).toRoomUi())
        }
    }
}

@Composable
fun RoomStateCard(roomState: RoomStateUi) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = roomState.roomNumber,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = roomState.bedCount,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(imageVector = Icons.Default.Bed, contentDescription = null)

            }
            Spacer(modifier = Modifier.height(4.dp))
            Column {
                Text(
                    text = stringResource(Res.string.cleaning_state),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = stringResource(roomState.cleaningType),
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            Column {
                Text(
                    text = stringResource(Res.string.cleaning_type),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.bodySmall
                )
                Row {
                    Text(
                        text = stringResource(roomState.state),
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    roomState.updatedAt?.let {
                        Text(
                            text = it,
                            fontWeight = FontWeight.SemiBold,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }
}


@Composable
@Preview
private fun RoomScreenPreview() {
    VientosDelSurTheme {
        Surface {
            RoomScreen()
        }
    }
}