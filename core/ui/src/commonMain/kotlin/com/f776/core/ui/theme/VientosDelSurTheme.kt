package com.f776.core.ui.theme

import androidx.compose.runtime.Composable

@Composable
expect fun VientosDelSurTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
)