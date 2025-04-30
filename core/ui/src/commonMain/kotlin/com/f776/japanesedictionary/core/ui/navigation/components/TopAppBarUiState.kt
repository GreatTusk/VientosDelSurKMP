package com.f776.japanesedictionary.core.ui.navigation.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable

internal data class TopAppBarUiState(
    val titleText: String? = null,
    val title: @Composable () -> Unit = {},
    val navAction: () -> Unit = {},
    val navContentDescription: String? = null,
    val actions: @Composable RowScope.() -> Unit = {}
)