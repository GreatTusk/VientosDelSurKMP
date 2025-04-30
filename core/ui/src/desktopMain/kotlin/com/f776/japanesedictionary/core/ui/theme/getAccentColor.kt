package com.f776.japanesedictionary.core.ui.theme

import androidx.compose.ui.graphics.Color

internal fun getAccentColor(): Color? {
    return try {
        AccentColorLibraryLoader().loadLibrary()
        val colorValue = AccentColorLibrary.INSTANCE.getAccentColor()

        colorValue?.toULong(16)?.toInt()?.let(::Color)
    } catch (e: Exception) {
        println("Error accessing native library: ${e.message}")
        null
    }
}