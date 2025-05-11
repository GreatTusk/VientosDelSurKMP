package com.portafolio.vientosdelsur.core.database.entity.employee

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object HousekeeperTable : IntIdTable("housekeeper") {
    val employeeId = reference("employee_id", EmployeeTable.id)
    val housekeeperRole = enumeration<HousekeeperRole>("housekeeper_role")

    init {
        transaction {
            SchemaUtils.create(this@HousekeeperTable)
        }
    }
}