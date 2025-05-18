package com.portafolio.vientosdelsur.data.housekeeping.mapper

import com.portafolio.vientosdelsur.core.database.entity.work.RoomCleaningType
import com.portafolio.vientosdelsur.domain.housekeeping.usecase.MonthlyRoomDistribution
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

internal suspend fun MonthlyRoomDistribution.toHousekeeperShiftEntities() = coroutineScope {
    map { (_, housekeeperShift) ->
        async {
            housekeeperShift.map { (shift, rooms) ->
                val workShiftId = shift.workShiftId
                rooms.map {
                    HousekeeperShiftEntity(
                        wordShiftId = workShiftId,
                        roomId = it.room.id,
                        roomCleaningType = when (it.cleaningType) {
                            com.portafolio.vientosdelsur.domain.room.model.RoomCleaningType.ROOM -> RoomCleaningType.ROOM
                            com.portafolio.vientosdelsur.domain.room.model.RoomCleaningType.GUEST -> RoomCleaningType.GUEST
                        }
                    )
                }
            }.flatten()
        }
    }.awaitAll().flatten()
}


internal data class HousekeeperShiftEntity(
    val wordShiftId: Int,
    val roomId: Int,
    val roomCleaningType: RoomCleaningType
)