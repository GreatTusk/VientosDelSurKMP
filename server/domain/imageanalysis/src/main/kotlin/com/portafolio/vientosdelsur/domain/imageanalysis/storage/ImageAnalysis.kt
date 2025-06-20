package com.portafolio.vientosdelsur.domain.imageanalysis.storage

import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.domain.imageanalysis.RoomAnalysisState
import com.portafolio.vientosdelsur.domain.imageanalysis.drawConclusion
import com.portafolio.vientosdelsur.domain.room.model.Room
import kotlinx.datetime.LocalDateTime

data class ImageAnalysis(
    val id: Int,
    val room: Room,
    val uploadedBy: Employee.Housekeeper,
    val updatedAt: LocalDateTime,
    val cleanProbability: Double,
    val uncleanProbability: Double,
    val imageUrl: String,
    val roomAnalysisState: RoomAnalysisState
) {
    val analysisConclusion = drawConclusion(cleanProbability)
}

data class SaveImageAnalysis(
    val roomId: Int,
    val housekeeperId: Int,
    val cleanProbability: Double,
    val uncleanProbability: Double
)