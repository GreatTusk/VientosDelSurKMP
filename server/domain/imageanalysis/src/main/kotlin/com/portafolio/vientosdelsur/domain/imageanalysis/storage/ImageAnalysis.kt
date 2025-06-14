package com.portafolio.vientosdelsur.domain.imageanalysis.storage

import com.portafolio.vientosdelsur.domain.room.Room
import kotlinx.datetime.LocalDateTime

data class ImageAnalysis(
    val id: Int,
    val room: Room,
    val updatedAt: LocalDateTime,
    val cleanProbability: Double,
    val uncleanProbability: Double,
    val image: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ImageAnalysis

        if (id != other.id) return false
        if (cleanProbability != other.cleanProbability) return false
        if (uncleanProbability != other.uncleanProbability) return false
        if (room != other.room) return false
        if (updatedAt != other.updatedAt) return false
        if (!image.contentEquals(other.image)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + cleanProbability.hashCode()
        result = 31 * result + uncleanProbability.hashCode()
        result = 31 * result + room.hashCode()
        result = 31 * result + updatedAt.hashCode()
        result = 31 * result + image.contentHashCode()
        return result
    }
}

data class SaveImageAnalysis(
    val roomId: Int,
    val cleanProbability: Double,
    val uncleanProbability: Double
)