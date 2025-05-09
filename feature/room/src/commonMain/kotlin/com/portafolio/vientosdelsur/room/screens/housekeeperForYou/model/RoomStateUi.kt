package com.portafolio.vientosdelsur.room.screens.housekeeperForYou.model

import com.portafolio.vientosdelsur.domain.room.*
import com.portafolio.vientosdelsur.room.screens.housekeeperForYou.util.HourFormatter
import org.jetbrains.compose.resources.StringResource
import vientosdelsur.feature.room.generated.resources.*
import vientosdelsur.feature.room.generated.resources.Res
import vientosdelsur.feature.room.generated.resources.cleaning_checkout
import vientosdelsur.feature.room.generated.resources.cleaning_guest
import vientosdelsur.feature.room.generated.resources.cleaning_out_of_order

data class RoomStateUi(
    val id: Int,
    val roomNumber: String,
    val bedCount: String,
    val cleaningType: StringResource,
    val state: StringResource,
    val updatedAt: String?
)

fun RoomState.toRoomUi(): RoomStateUi {
    val (state, hour) = when (val status = roomCleaningStatus) {
        is RoomCleaningStatus.Done -> Res.string.done_state to status.changedAt
        is RoomCleaningStatus.InCleaning -> Res.string.in_cleaning_state to status.changedAt
        is RoomCleaningStatus.InRevision -> Res.string.in_revision_state to status.changedAt
        RoomCleaningStatus.Pending -> Res.string.pending_state to null
    }

    return RoomStateUi(
        id = room.id,
        roomNumber = room.number.toString(),
        bedCount = when (room.roomType) {
            RoomType.SINGLE -> 1
            RoomType.DOUBLE -> 2
            RoomType.TRIPLE -> 3
            RoomType.QUAD -> 4
        }.toString(),
        cleaningType = when(roomCleaningType) {
            RoomCleaningType.ROOM -> Res.string.cleaning_checkout
            RoomCleaningType.GUEST -> Res.string.cleaning_guest
            RoomCleaningType.OUT_OF_ORDER -> Res.string.cleaning_out_of_order
        },
        state = state,
        updatedAt = hour?.let { HourFormatter.hourFormatter.format(it.time) }
    )
}