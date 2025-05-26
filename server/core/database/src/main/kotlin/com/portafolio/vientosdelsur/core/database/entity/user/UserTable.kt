package com.portafolio.vientosdelsur.core.database.entity.user

import com.portafolio.vientosdelsur.core.database.entity.employee.EmployeeEntity
import com.portafolio.vientosdelsur.core.database.entity.employee.EmployeeTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object UserTable : IdTable<String>("user") {
    override val id = varchar("id", 128).entityId()
    val email = varchar("email", 100)
    val photoUrl = varchar("photo_url", 255).nullable()
    val phoneNumber = varchar("phone_number", 9)
    val isEnabled = bool("registration_completed").default(false)
    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at")

    override val primaryKey = PrimaryKey(id)
}

class UserEntity(id: EntityID<String>) : Entity<String>(id) {
    companion object : EntityClass<String, UserEntity>(UserTable)

    var email by UserTable.email
    var photoUrl by UserTable.photoUrl
    var phoneNumber by UserTable.phoneNumber
    var isEnabled by UserTable.isEnabled
    var createdAt by UserTable.createdAt
    var updatedAt by UserTable.updatedAt

    val employee by EmployeeEntity optionalBackReferencedOn EmployeeTable
}