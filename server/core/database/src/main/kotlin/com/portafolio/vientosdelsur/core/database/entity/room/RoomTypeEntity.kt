package com.portafolio.vientosdelsur.core.database.entity.room

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

internal object RoomTypeEntity: IntIdTable("room_type") {
    val roomType = enumeration<RoomType>("room_type")
    val workUnit = integer("work_unit")
    val checkOutWorkUnit = integer("checkout_work_unit")

    init {
        transaction {
            SchemaUtils.create(this@RoomTypeEntity)
        }
    }
}

enum class RoomType(val beds: Int) {
    SINGLE(1), DOUBLE(2), TRIPLE(3), QUAD(4)
}