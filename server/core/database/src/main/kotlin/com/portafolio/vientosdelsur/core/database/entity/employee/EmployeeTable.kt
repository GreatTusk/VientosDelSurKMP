package com.portafolio.vientosdelsur.core.database.entity.employee

import com.portafolio.vientosdelsur.core.database.entity.work.WorkShiftEntity
import com.portafolio.vientosdelsur.core.database.entity.work.WorkShiftTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object EmployeeTable : IntIdTable("employee") {
    val firstName = varchar("first_name", 50)
    val lastName = varchar("last_name", 50)
    val email = varchar("email", 50).uniqueIndex()
    val phoneNumber = varchar("phone_number", 9)
    val hireDate = datetime("hire_date")
    val dayOff = integer("day_off").nullable().check { it.between(1, 6) }
    val occupation = enumeration<Occupation>("occupation")
}

class EmployeeEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<EmployeeEntity>(EmployeeTable)

    var firstName by EmployeeTable.firstName
    var lastName by EmployeeTable.lastName
    var email by EmployeeTable.email
    var phoneNumber by EmployeeTable.phoneNumber
    var dayOff by EmployeeTable.dayOff
    var hireDate by EmployeeTable.hireDate
    var occupation by EmployeeTable.occupation

    val workShifts by WorkShiftEntity referrersOn WorkShiftTable.employeeId

    val housekeeper: HousekeeperEntity?
        get() = HousekeeperEntity.findById(this.id)
}