package com.f776.japanesedictionary.imageanalysis.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KingBed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.f776.japanesedictionary.imageanalysis.model.RoomSelectionUi
import org.jetbrains.compose.resources.stringResource
import vientosdelsur.feature.imageanalysis.generated.resources.*

@Composable
internal fun RoomSelectionDialog(
    rooms: List<RoomSelectionUi>,
    selectedRoom: RoomSelectionUi?,
    onRoomSelected: (RoomSelectionUi) -> Unit,
    onDismissRequest: () -> Unit
) {
    val roomsByFloor = remember(rooms) {
        rooms.groupBy { it.room }.toMap()
    }

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Card(
            modifier = Modifier
                .heightIn(max = 600.dp)
                .fillMaxWidth()
                .padding(16.dp),
            shape = MaterialTheme.shapes.large
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = stringResource(Res.string.select_room),
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                LazyColumn(
                    modifier = Modifier
                        .weight(1f, fill = false)
                        .fillMaxWidth()
                ) {
                    roomsByFloor.forEach { (floor, roomsInFloor) ->
                        item {
                            Text(
                                text = stringResource(Res.string.floor, floor),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                            HorizontalDivider()
                        }

                        items(roomsInFloor) { room ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .selectable(
                                        selected = room == selectedRoom,
                                        onClick = {
                                            onRoomSelected(room)
                                            onDismissRequest()
                                        },
                                        role = Role.RadioButton
                                    )
                                    .padding(vertical = 8.dp, horizontal = 16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = room == selectedRoom,
                                    onClick = null
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(
                                    imageVector = Icons.Filled.KingBed,
                                    contentDescription = "Room",
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = room.number,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = onDismissRequest
                    ) {
                        Text(stringResource(Res.string.cancel))
                    }
                }
            }
        }
    }
}
