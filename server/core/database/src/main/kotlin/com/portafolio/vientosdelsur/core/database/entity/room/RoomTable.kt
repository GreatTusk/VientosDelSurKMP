package com.portafolio.vientosdelsur.core.database.entity.room

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object RoomTable : IntIdTable("room") {
    val roomNumber = varchar("room_number", 3).uniqueIndex()
    val roomTypeId = reference("room_type_id", RoomTypeTable)

    init {
        transaction {
            if (SchemaUtils.listTables()
                    .any { it.contains(this@RoomTable.tableName) }
            ) return@transaction

            SchemaUtils.create(this@RoomTable)


        }
    }
}


class RoomEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<RoomEntity>(RoomTable)

    var roomNumber by RoomTable.roomNumber
    val roomType by RoomTypeEntity backReferencedOn RoomTable.roomTypeId
}
