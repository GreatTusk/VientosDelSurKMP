package com.portafolio.vientosdelsur.core.database.entity

import MigrationUtils
import com.portafolio.vientosdelsur.core.database.entity.booking.GuestTable
import com.portafolio.vientosdelsur.core.database.entity.booking.RoomBookingTable
import com.portafolio.vientosdelsur.core.database.entity.employee.EmployeeTable
import com.portafolio.vientosdelsur.core.database.entity.employee.HousekeeperTable
import com.portafolio.vientosdelsur.core.database.entity.room.*
import com.portafolio.vientosdelsur.core.database.entity.room.RoomTable.roomNumber
import com.portafolio.vientosdelsur.core.database.entity.room.RoomTable.roomTypeId
import com.portafolio.vientosdelsur.core.database.entity.room.RoomTypeTable.checkOutWorkUnit
import com.portafolio.vientosdelsur.core.database.entity.room.RoomTypeTable.roomType
import com.portafolio.vientosdelsur.core.database.entity.room.RoomTypeTable.workUnit
import com.portafolio.vientosdelsur.core.database.entity.work.HousekeeperShiftRoomTable
import com.portafolio.vientosdelsur.core.database.entity.work.WorkShiftTable
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object SchemaCreation {
    fun initializeDatabase() = transaction {
        MigrationUtils.statementsRequiredForDatabaseMigration(
            RoomTypeTable,
            RoomTable,
            EmployeeTable,
            HousekeeperTable,
            GuestTable,
            RoomBookingTable,
            WorkShiftTable,
            HousekeeperShiftRoomTable,
            RoomStatusTable
        ).forEach(::exec)

        if (RoomTypeEntity.count() == 0L) {
            initializeRoomTypeData()
        }

        if (RoomEntity.count() == 0L) {
            initializeRoomData()
        }
    }

    private fun Transaction.initializeRoomTypeData() {
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

    private fun Transaction.initializeRoomData() {
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

        val roomTypeMap = RoomTypeTable.selectAll()
            .associate { it[roomType] to it[RoomTypeTable.id] }

        // Batch insert all rooms
        RoomTable.batchInsert(roomData) { (number, type) ->
            this[roomNumber] = number
            this[roomTypeId] = roomTypeMap[type] ?: error("RoomType $type not found in database")
        }
    }
}