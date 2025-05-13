package com.portafolio.vientosdelsur.room.screens.foryou.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.portafolio.vientosdelsur.room.screens.foryou.housekeeper.model.RoomStateUi
import org.jetbrains.compose.resources.stringResource
import vientosdelsur.feature.room.generated.resources.Res
import vientosdelsur.feature.room.generated.resources.cleaning_state
import vientosdelsur.feature.room.generated.resources.cleaning_type

@Composable
internal fun RoomStateCard(roomState: RoomStateUi) {
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
