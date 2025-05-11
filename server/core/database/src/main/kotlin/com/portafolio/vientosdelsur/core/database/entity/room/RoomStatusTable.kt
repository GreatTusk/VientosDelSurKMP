package com.portafolio.vientosdelsur.core.database.entity.room

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object RoomStatusTable : IntIdTable("room_status") {
    val roomStatus = enumeration<RoomStatus>("room_status")
    val roomId = reference("room_id", RoomTable)
    val updatedAt = datetime("updated_at")
}

class RoomStatusEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<RoomStatusEntity>(RoomStatusTable)

    var roomStatus by RoomStatusTable.roomStatus
    var room by RoomEntity referencedOn RoomStatusTable.roomId
    var updatedAt by RoomStatusTable.updatedAt
}