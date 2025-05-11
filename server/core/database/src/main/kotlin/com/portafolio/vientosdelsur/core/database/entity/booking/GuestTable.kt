package com.portafolio.vientosdelsur.core.database.entity.booking

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object GuestTable: IntIdTable("guest") {
    val firstName = varchar("first_name", 50)
    val lastName = varchar("last_name", 50)
    val email = varchar("email", 50).uniqueIndex()
    val phoneNumber = varchar("phone_number", 9)
}

class GuestEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<GuestEntity>(GuestTable)

    var firstName by GuestTable.firstName
    var lastName by GuestTable.lastName
    var email by GuestTable.email
    var phoneNumber by GuestTable.phoneNumber
}