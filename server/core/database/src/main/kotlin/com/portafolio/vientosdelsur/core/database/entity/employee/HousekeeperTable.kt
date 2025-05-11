package com.portafolio.vientosdelsur.core.database.entity.employee

import com.portafolio.vientosdelsur.core.database.entity.work.WorkShiftEntity
import com.portafolio.vientosdelsur.core.database.entity.work.WorkShiftTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object HousekeeperTable : IdTable<Int>("housekeeper") {
    val employeeId = reference("employee_id", EmployeeTable)
    val housekeeperRole = enumeration<HousekeeperRole>("housekeeper_role")

    override val id: Column<EntityID<Int>> = employeeId
    override val primaryKey = PrimaryKey(employeeId)
}

class HousekeeperEntity(id: EntityID<Int>) : Entity<Int>(id) {
    companion object : EntityClass<Int, HousekeeperEntity>(HousekeeperTable)

    val employee by EmployeeEntity backReferencedOn HousekeeperTable.employeeId
    var housekeeperRole by HousekeeperTable.housekeeperRole

    val shifts by WorkShiftEntity referrersOn WorkShiftTable.employeeId
}