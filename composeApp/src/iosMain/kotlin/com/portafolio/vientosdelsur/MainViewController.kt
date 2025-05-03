package com.portafolio.vientosdelsur

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController() = ComposeUIViewController { App(isSystemInDarkTheme()) }