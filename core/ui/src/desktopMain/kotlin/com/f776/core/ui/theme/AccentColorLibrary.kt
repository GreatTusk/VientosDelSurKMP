package com.f776.core.ui.theme

import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.Platform

internal interface AccentColorLibrary : Library {
    companion object {
        val INSTANCE: AccentColorLibrary = Native.load(
            when {
                Platform.isLinux() -> "accent_color"
                Platform.isWindows() -> "accent_color"
                Platform.isMac() -> "accent_color"
                else -> throw UnsupportedOperationException("Platform not supported")
            },
            AccentColorLibrary::class.java
        )
    }

    fun getAccentColor(): String?
}