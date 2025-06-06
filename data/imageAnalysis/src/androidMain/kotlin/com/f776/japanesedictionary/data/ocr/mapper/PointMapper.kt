package com.f776.japanesedictionary.data.ocr.mapper

import com.f776.japanesedictionary.domain.imageanalysis.Point

internal fun android.graphics.Point.toDomainPoint(): Point {
    return Point(x = x, y = y)
}