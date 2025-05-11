package com.portafolio.vientosdelsur.core.database.entity.work

import com.portafolio.vientosdelsur.core.database.entity.room.RoomTable
import com.portafolio.vientosdelsur.shared.domain.RoomCleaningType
import org.jetbrains.exposed.dao.id.CompositeIdTable

object HousekeeperShiftRoomTable : CompositeIdTable("housekeeper_shift") {
    val wordShiftId = reference("work_shift_id", WorkShiftTable)
    val roomId = reference("room_id", RoomTable)
    val roomCleaningType = enumeration<RoomCleaningType>("room_cleaning_type")

    override val primaryKey = PrimaryKey(wordShiftId, roomId, roomCleaningType)
}