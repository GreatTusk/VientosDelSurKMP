package com.portafolio.vientosdelsur.core.database.entity.room

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

internal object RoomEntity : IntIdTable("room") {
    val roomNumber = varchar("room_number", 3)
    val roomTypeId = reference("room_type_id", RoomTypeEntity.id)

    init {
        transaction {
            SchemaUtils.create(this@RoomEntity)
        }
    }
}