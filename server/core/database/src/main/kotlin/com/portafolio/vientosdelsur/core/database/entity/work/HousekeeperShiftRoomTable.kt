package com.portafolio.vientosdelsur.core.database.entity.work

import com.portafolio.vientosdelsur.core.database.entity.room.RoomEntity
import com.portafolio.vientosdelsur.core.database.entity.room.RoomTable
import com.portafolio.vientosdelsur.shared.domain.RoomCleaningType
import org.jetbrains.exposed.dao.CompositeEntity
import org.jetbrains.exposed.dao.CompositeEntityClass
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.dao.id.CompositeIdTable
import org.jetbrains.exposed.dao.id.EntityID

object HousekeeperShiftRoomTable : CompositeIdTable("housekeeper_shift") {
    val wordShiftId = reference("work_shift_id", WorkShiftTable)
    val roomId = reference("room_id", RoomTable)
    val roomCleaningType = enumeration<RoomCleaningType>("room_cleaning_type")

    override val primaryKey = PrimaryKey(wordShiftId, roomId, roomCleaningType)
}

class HousekeeperShiftRoomEntity(id: EntityID<CompositeID>) : CompositeEntity(id) {
    companion object : CompositeEntityClass<HousekeeperShiftRoomEntity>(HousekeeperShiftRoomTable)

    val wordShiftId by WorkShiftEntity backReferencedOn HousekeeperShiftRoomTable.wordShiftId
    var room by RoomEntity referencedOn HousekeeperShiftRoomTable
    val roomCleaningType by HousekeeperShiftRoomTable.roomCleaningType
}