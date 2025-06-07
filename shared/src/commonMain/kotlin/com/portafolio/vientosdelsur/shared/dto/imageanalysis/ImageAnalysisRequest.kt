package com.portafolio.vientosdelsur.shared.dto.imageanalysis

import kotlinx.serialization.Serializable

@Serializable
data class ImageAnalysisRequest(
    val roomId: Int,
    val imageBytes: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ImageAnalysisRequest

        if (roomId != other.roomId) return false
        if (!imageBytes.contentEquals(other.imageBytes)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = roomId
        result = 31 * result + imageBytes.contentHashCode()
        return result
    }
}