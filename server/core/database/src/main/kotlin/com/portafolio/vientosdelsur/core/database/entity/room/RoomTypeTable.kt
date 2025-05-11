package com.portafolio.vientosdelsur.core.database.entity.room

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.transactions.transaction

object RoomTypeTable : IntIdTable("room_type") {
    val roomType = enumeration<RoomType>("room_type").uniqueIndex()
    val workUnit = integer("work_unit")
    val checkOutWorkUnit = integer("checkout_work_unit")

    init {
        transaction {
            if (SchemaUtils.listTables()
                    .any { it.contains(this@RoomTypeTable.tableName) }
            ) return@transaction
            SchemaUtils.create(this@RoomTypeTable)

            // Batch insert all room types with their respective work units
            RoomTypeTable.batchInsert(RoomType.entries) { roomTypeValue ->
                this[roomType] = roomTypeValue
                this[workUnit] = when (roomTypeValue) {
                    RoomType.SINGLE -> 18
                    RoomType.DOUBLE -> 28
                    RoomType.TRIPLE -> 42
                    RoomType.QUAD -> 56
                }
                this[checkOutWorkUnit] = when (roomTypeValue) {
                    RoomType.SINGLE -> 25
                    RoomType.DOUBLE -> 40
                    RoomType.TRIPLE -> 60
                    RoomType.QUAD -> 80
                }
            }
        }
    }
}

class RoomTypeEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<RoomTypeEntity>(RoomTypeTable)

    var roomType by RoomTypeTable.roomType
    var workUnit by RoomTypeTable.workUnit
    var checkOutWorkUnit by RoomTypeTable.checkOutWorkUnit
}