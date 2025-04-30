package com.f776.japanesedictionary.domain.ocr

data class OCRLine(
    val text: String,
    val boundingBox: Rect,
    val recognizedLanguage: String,
    val points: Array<Point>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as OCRLine

        if (text != other.text) return false
        if (boundingBox != other.boundingBox) return false
        if (recognizedLanguage != other.recognizedLanguage) return false
        if (!points.contentEquals(other.points)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = text.hashCode()
        result = 31 * result + boundingBox.hashCode()
        result = 31 * result + recognizedLanguage.hashCode()
        result = 31 * result + points.contentHashCode()
        return result
    }
}