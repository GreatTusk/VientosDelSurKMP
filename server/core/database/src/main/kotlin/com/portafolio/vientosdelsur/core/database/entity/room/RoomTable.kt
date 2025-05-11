package com.portafolio.vientosdelsur.core.database.entity.room

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object RoomTable : IntIdTable("room") {
    val roomNumber = varchar("room_number", 3).uniqueIndex()
    val roomTypeId = reference("room_type_id", RoomTypeTable)
}


class RoomEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<RoomEntity>(RoomTable)

    var roomNumber by RoomTable.roomNumber
    val roomType by RoomTypeEntity referencedOn RoomTable.roomTypeId
}
