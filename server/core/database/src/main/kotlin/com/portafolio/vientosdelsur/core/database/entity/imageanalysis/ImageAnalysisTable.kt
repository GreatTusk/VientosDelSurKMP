package com.portafolio.vientosdelsur.core.database.entity.imageanalysis

import com.portafolio.vientosdelsur.core.database.entity.employee.EmployeeEntity
import com.portafolio.vientosdelsur.core.database.entity.employee.EmployeeTable
import com.portafolio.vientosdelsur.core.database.entity.room.RoomEntity
import com.portafolio.vientosdelsur.core.database.entity.room.RoomTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object ImageAnalysisTable: IntIdTable("image_analysis") {
    val roomId = reference("room_id", RoomTable)
    val image = blob("image")
    val uploadedAt = datetime("uploaded_at")
    val uploadedBy = reference("uploaded_by_housekeeper_id", EmployeeTable)
    // TODO: Consider making a separate table to keep a history
    val roomAnalysisStatus = enumeration<RoomAnalysisTable>("room_analysis_status").default(RoomAnalysisTable.PENDING)
    val cleanProbability = double("clean_prob")
    val uncleanProbability = double("unclean_prob")
}

class ImageAnalysisEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<ImageAnalysisEntity>(ImageAnalysisTable)

    var room by RoomEntity referencedOn ImageAnalysisTable
    var housekeeper by EmployeeEntity referencedOn ImageAnalysisTable
    var image by ImageAnalysisTable.image
    var uploadedAt by ImageAnalysisTable.uploadedAt
    var cleanProbability by ImageAnalysisTable.cleanProbability
    var uncleanProbability by ImageAnalysisTable.uncleanProbability
    var roomAnalysisStatus by ImageAnalysisTable.roomAnalysisStatus
}