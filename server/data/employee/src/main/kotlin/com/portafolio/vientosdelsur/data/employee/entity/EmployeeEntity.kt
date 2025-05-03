package com.portafolio.vientosdelsur.data.employee.entity

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import org.jetbrains.exposed.sql.transactions.transaction

internal object EmployeeEntity: IntIdTable("employee") {
    val firstName = varchar("first_name", 50)
    val lastName = varchar("last_name", 50)
    val phoneNumber = varchar("phone_number", 9)
    val dayOff = varchar("day_off", 50).nullable()
    val hireDate = datetime("hire_date")
    val occupation = varchar("occupation", 50)

    init {
        transaction {
            SchemaUtils.create(this@EmployeeEntity)
        }
    }
}

internal class EmployeeDao(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<EmployeeDao>(EmployeeEntity)

    var firstName by EmployeeEntity.firstName
    var lastName by EmployeeEntity.lastName
    var phoneNumber by EmployeeEntity.phoneNumber
    var dayOff by EmployeeEntity.dayOff
    var hireDate by EmployeeEntity.hireDate
    var occupation by EmployeeEntity.occupation
}