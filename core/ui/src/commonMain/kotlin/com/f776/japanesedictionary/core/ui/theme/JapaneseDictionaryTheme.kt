package com.f776.japanesedictionary.core.ui.theme

import androidx.compose.runtime.Composable

@Composable
expect fun JapaneseDictionaryTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
)