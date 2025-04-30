package com.f776.japanesedictionary.core.ui.theme

import com.sun.jna.Platform
import java.io.File
import kotlin.io.path.createTempDirectory

class AccentColorLibraryLoader {
    fun loadLibrary() {
        val libraryName = when {
            Platform.isLinux() -> "libaccent_color.so"
            Platform.isWindows() -> "libaccent_color.dll"
            Platform.isMac() -> "accent_color.framework/accent_color"
            else -> throw UnsupportedOperationException("Unsupported platform")
        }

        val nativePath = when {
            Platform.isLinux() -> "natives/linux-x86-64"
            Platform.isWindows() -> "natives/win32-x86-64"
            Platform.isMac() -> "natives/darwin-x86-64"
            else -> throw UnsupportedOperationException("Unsupported platform")
        }

        // Create a temporary directory to extract library
        val tempDir = createTempDirectory("natives").toFile()
        tempDir.deleteOnExit()

        // Extract the library to the temporary directory
        val inputStream = javaClass.classLoader?.getResourceAsStream("$nativePath/$libraryName")
            ?: throw RuntimeException("Could not find native library: $libraryName in $nativePath/$libraryName")

        val tempFile = File(tempDir, libraryName)
        tempFile.parentFile.mkdirs()

        inputStream.use { input ->
            tempFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }

        // Tell JNA to look in this directory
        System.setProperty("jna.library.path", tempDir.absolutePath)
    }
}