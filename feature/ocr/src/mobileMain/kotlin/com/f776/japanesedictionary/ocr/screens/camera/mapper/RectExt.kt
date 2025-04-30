package com.f776.japanesedictionary.ocr.screens.camera.mapper

import com.f776.japanesedictionary.domain.ocr.Rect

internal fun Rect.mapToViewCoordinates(
    sourceWidth: Int,
    sourceHeight: Int,
    viewWidth: Float,
    viewHeight: Float
): Rect {
    val scaleX = viewWidth / sourceWidth
    val scaleY = viewHeight / sourceHeight

    return Rect(
        left = (left * scaleX).toInt(),
        top = (top * scaleY).toInt(),
        right = (right * scaleX).toInt(),
        bottom = (bottom * scaleY).toInt()
    )
}

internal fun Rect.toComposeRect() =
    androidx.compose.ui.geometry.Rect(
        left = left.toFloat(),
        top = top.toFloat(),
        right = right.toFloat(),
        bottom = bottom.toFloat()
    )