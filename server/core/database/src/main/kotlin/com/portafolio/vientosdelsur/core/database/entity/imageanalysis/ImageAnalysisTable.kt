package com.portafolio.vientosdelsur.core.database.entity.imageanalysis

import com.portafolio.vientosdelsur.core.database.entity.room.RoomEntity
import com.portafolio.vientosdelsur.core.database.entity.room.RoomStatusTable
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
    val cleanProbability = double("clean_prob")
    val uncleanProbability = double("unclean_prob")
}

class ImageAnalysisEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<ImageAnalysisEntity>(ImageAnalysisTable)

    var room by RoomEntity referencedOn RoomStatusTable.roomId
    var image by ImageAnalysisTable.image
    var uploadedAt by ImageAnalysisTable.uploadedAt
    var cleanProbability by ImageAnalysisTable.cleanProbability
    var uncleanProbability by ImageAnalysisTable.uncleanProbability
}