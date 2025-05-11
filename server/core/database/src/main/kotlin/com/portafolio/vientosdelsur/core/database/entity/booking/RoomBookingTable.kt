package com.portafolio.vientosdelsur.core.database.entity.booking

import com.portafolio.vientosdelsur.core.database.entity.room.RoomTable
import com.portafolio.vientosdelsur.core.database.entity.room.RoomTypeEntity
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.date

object RoomBookingTable : IntIdTable("room_booking") {
    val startDate = date("start_date")
    val endDate = date("end_date")
    val roomId = reference("room_id", RoomTable)
    val guestId = reference("guest_id", GuestTable)

    init {
        check { endDate greater startDate }
        uniqueIndex(roomId, startDate, endDate)
    }
}

class RoomBookingEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<RoomBookingEntity>(RoomBookingTable)

    var startDate by RoomBookingTable.startDate
    var endDate by RoomBookingTable.endDate
    var room by RoomTypeEntity referencedOn RoomBookingTable.roomId
    var guest by GuestEntity referencedOn RoomBookingTable.guestId
}