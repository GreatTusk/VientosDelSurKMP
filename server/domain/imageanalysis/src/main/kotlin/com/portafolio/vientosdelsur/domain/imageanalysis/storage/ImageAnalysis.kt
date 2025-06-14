package com.portafolio.vientosdelsur.domain.imageanalysis.storage

import com.portafolio.vientosdelsur.domain.imageanalysis.drawConclusion
import com.portafolio.vientosdelsur.domain.room.model.Room
import kotlinx.datetime.LocalDateTime

data class ImageAnalysis(
    val id: Int,
    val room: Room,
    val updatedAt: LocalDateTime,
    val cleanProbability: Double,
    val uncleanProbability: Double,
    val image: String
) {
    val analysisConclusion = drawConclusion(cleanProbability)
}

data class SaveImageAnalysis(
    val roomId: Int,
    val cleanProbability: Double,
    val uncleanProbability: Double
)