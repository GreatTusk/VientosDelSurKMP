package com.f776.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.f776.core.ui.theme.AppTypography
import com.f776.core.ui.theme.darkScheme
import com.f776.core.ui.theme.lightScheme

@Composable
actual fun VientosDelSurTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) darkScheme else lightScheme,
        typography = AppTypography(),
        content = content
    )
}