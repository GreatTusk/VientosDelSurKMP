package com.f776.japanesedictionary.domain.ocr

data class Rect(
    val bottom: Int,
    val left: Int,
    val right: Int,
    val top: Int
) {
    val width: Int
        get() = right - left

    val height: Int
        get() = bottom - top
}