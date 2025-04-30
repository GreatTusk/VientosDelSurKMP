package com.f776.japanesedictionary.domain.ocr


data class OCRResult(
    val text: String,
    val textLines: List<OCRLine> = emptyList()
) {
    val isEmpty: Boolean
        get() = text.isEmpty() && textLines.isEmpty()
}