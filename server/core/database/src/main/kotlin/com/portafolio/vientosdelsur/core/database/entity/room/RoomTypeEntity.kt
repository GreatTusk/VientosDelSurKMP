package com.portafolio.vientosdelsur.core.database.entity.room

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.transactions.transaction

object RoomTypeEntity : IntIdTable("room_type") {
    val roomType = enumeration<RoomType>("room_type").uniqueIndex()
    val workUnit = integer("work_unit")
    val checkOutWorkUnit = integer("checkout_work_unit")

    init {
        transaction {
            if (SchemaUtils.listTables()
                    .any { it.contains(this@RoomTypeEntity.tableName) }
            ) return@transaction
            SchemaUtils.create(this@RoomTypeEntity)

            // Batch insert all room types with their respective work units
            RoomTypeEntity.batchInsert(RoomType.entries) { roomTypeValue ->
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

class RoomTypeDao(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<RoomTypeDao>(RoomTypeEntity)

    var roomType by RoomTypeEntity.roomType
    var workUnit by RoomTypeEntity.workUnit
    var checkOutWorkUnit by RoomTypeEntity.checkOutWorkUnit
}

enum class RoomType(val beds: Int) {
    SINGLE(1), DOUBLE(2), TRIPLE(3), QUAD(4)
}