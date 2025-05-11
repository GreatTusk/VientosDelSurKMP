package com.portafolio.vientosdelsur.core.database.entity.room

import com.portafolio.vientosdelsur.shared.domain.RoomType
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object RoomTypeTable : IntIdTable("room_type") {
    val roomType = enumeration<RoomType>("room_type").uniqueIndex()
    val workUnit = integer("work_unit")
    val checkOutWorkUnit = integer("checkout_work_unit")
}

class RoomTypeEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<RoomTypeEntity>(RoomTypeTable)

    var roomType by RoomTypeTable.roomType
    var workUnit by RoomTypeTable.workUnit
    var checkOutWorkUnit by RoomTypeTable.checkOutWorkUnit

    val rooms by RoomEntity referrersOn RoomTable.roomTypeId
}