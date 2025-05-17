package com.portafolio.vientosdelsur.core.database.entity.work

import com.portafolio.vientosdelsur.core.database.entity.employee.EmployeeEntity
import com.portafolio.vientosdelsur.core.database.entity.employee.EmployeeTable
import com.portafolio.vientosdelsur.core.database.entity.room.RoomEntity
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.date

object WorkShiftTable : IntIdTable("work_shift") {
    val date = date("date")
    val employeeId = reference("employee_id", EmployeeTable.id)
    val shift = enumeration<Shift>("shift")

    init {
        uniqueIndex(date, employeeId, shift)
    }
}

class WorkShiftEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<WorkShiftEntity>(WorkShiftTable)

    var date by WorkShiftTable.date
    var employee by EmployeeEntity referencedOn WorkShiftTable.employeeId
    var shift by WorkShiftTable.shift
    var rooms by RoomEntity via HousekeeperShiftRoomTable

    val housekeeperShift by HousekeeperShiftRoomEntity optionalBackReferencedOn HousekeeperShiftRoomTable
}