package com.portafolio.vientosdelsur.core.database.entity.room

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object RoomEntity : IntIdTable("room") {
    val roomNumber = varchar("room_number", 3).uniqueIndex()
    val roomTypeId = reference("room_type_id", RoomTypeEntity.id)

    init {
        transaction {
            SchemaUtils.create(this@RoomEntity)

            val roomData = listOf(
                // First floor
                "101" to RoomType.TRIPLE,
                "102" to RoomType.DOUBLE,
                "104" to RoomType.SINGLE,
                "105" to RoomType.DOUBLE,

                // Second floor
                "206" to RoomType.DOUBLE,
                "207" to RoomType.DOUBLE,
                "208" to RoomType.DOUBLE,
                "209" to RoomType.DOUBLE,
                "210" to RoomType.DOUBLE,
                "211" to RoomType.DOUBLE,
                "212" to RoomType.DOUBLE,
                "213" to RoomType.DOUBLE,
                "214" to RoomType.DOUBLE,
                "215" to RoomType.DOUBLE,
                "216" to RoomType.DOUBLE,

                // Third floor
                "317" to RoomType.TRIPLE,
                "318" to RoomType.QUAD,
                "319" to RoomType.DOUBLE,
                "320" to RoomType.DOUBLE,
                "321" to RoomType.TRIPLE,
                "322" to RoomType.QUAD,
                "323" to RoomType.DOUBLE,

                // Fourth floor
                "424" to RoomType.QUAD,
                "425" to RoomType.QUAD,
                "426" to RoomType.DOUBLE,
                "427" to RoomType.DOUBLE,
                "428" to RoomType.DOUBLE,
                "429" to RoomType.DOUBLE
            )

            val roomTypeMap = RoomTypeEntity.selectAll()
                .associate { it[RoomTypeEntity.roomType] to it[RoomTypeEntity.id] }

            // Batch insert all rooms
            RoomEntity.batchInsert(roomData) { (number, type) ->
                this[roomNumber] = number
                this[roomTypeId] = roomTypeMap[type] ?: error("RoomType $type not found in database")
            }
        }
    }
}


class RoomDao(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<RoomDao>(RoomEntity)

    var roomNumber by RoomEntity.roomNumber
    var roomType by RoomTypeDao referencedOn RoomEntity.roomTypeId
}
