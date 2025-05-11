package com.portafolio.vientosdelsur.core.database.entity.work

import com.portafolio.vientosdelsur.core.database.entity.room.RoomTable
import org.jetbrains.exposed.dao.id.CompositeIdTable

object HousekeeperShiftRoomTable : CompositeIdTable("housekeeper_shift") {
    val wordShiftId = reference("work_shift_id", WorkShiftTable)
    val roomId = reference("room_id", RoomTable)

    override val primaryKey = PrimaryKey(wordShiftId, roomId)
}