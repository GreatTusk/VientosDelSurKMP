package com.f776.japanesedictionary.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.materialkolor.rememberDynamicColorScheme

@Composable
actual fun JapaneseDictionaryTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable () -> Unit
) {
    val accentColor = remember { getAccentColor() }
    val colorScheme = if (accentColor != null) {
        rememberDynamicColorScheme(accentColor, isDark = darkTheme, isAmoled = false)
    } else {
        if (darkTheme) darkScheme else lightScheme
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography(),
        content = content
    )
}