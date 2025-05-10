package com.portafolio.vientosdelsur.core.database.entity.employee

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import org.jetbrains.exposed.sql.transactions.transaction

internal object EmployeeEntity : IntIdTable("employee") {
    val firstName = varchar("first_name", 50)
    val lastName = varchar("last_name", 50)
    val email = varchar("email", 50)
    val phoneNumber = varchar("phone_number", 9)
    val hireDate = datetime("hire_date")
    val dayOff = integer("day_off").nullable()
    val occupation = enumeration<Occupation>("occupation")

    init {
        transaction {
            SchemaUtils.create(this@EmployeeEntity)
        }
    }
}

internal class EmployeeDao(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<EmployeeDao>(EmployeeEntity)

    var firstName by EmployeeEntity.firstName
    var lastName by EmployeeEntity.lastName
    var email by EmployeeEntity.email
    var phoneNumber by EmployeeEntity.phoneNumber
    var dayOff by EmployeeEntity.dayOff
    var hireDate by EmployeeEntity.hireDate
    var occupation by EmployeeEntity.occupation
}