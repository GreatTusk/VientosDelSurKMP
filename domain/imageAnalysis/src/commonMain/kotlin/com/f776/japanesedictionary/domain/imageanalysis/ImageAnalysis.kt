package com.f776.japanesedictionary.domain.imageanalysis

import com.portafolio.vientosdelsur.domain.room.Room
import kotlinx.datetime.LocalDateTime

data class ImageAnalysis(
    val id: Int,
    val room: Room,
    val updatedAt: LocalDateTime,
    val result: ImageAnalysisResult,
    val imageUrl: String
)
