package com.portafolio.vientosdelsur.core.database.entity.employee

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

internal object HousekeeperEntity : IntIdTable("housekeeper") {
    val employeeId = reference("employee_id", EmployeeEntity.id)
    val housekeeperRole = enumeration<HousekeeperRole>("housekeeper_role")

    init {
        transaction {
            SchemaUtils.create(this@HousekeeperEntity)
        }
    }
}