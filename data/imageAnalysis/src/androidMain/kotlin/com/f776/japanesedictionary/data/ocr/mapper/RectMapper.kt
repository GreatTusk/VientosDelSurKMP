package com.f776.japanesedictionary.data.ocr.mapper

import com.f776.japanesedictionary.domain.imageanalysis.Rect

internal fun android.graphics.Rect.toDomainRect(): Rect {
    return Rect(bottom = bottom, left = left, right = right, top = top)
}