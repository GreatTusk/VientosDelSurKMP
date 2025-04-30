package com.f776.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.f776.core.ui.theme.AppTypography
import com.f776.core.ui.theme.darkScheme
import com.f776.core.ui.theme.lightScheme
import com.materialkolor.rememberDynamicColorScheme

@Composable
actual fun VientosDelSurTheme(
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