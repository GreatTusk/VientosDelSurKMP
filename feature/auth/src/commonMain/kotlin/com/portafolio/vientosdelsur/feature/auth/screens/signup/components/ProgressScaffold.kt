@file:OptIn(ExperimentalMaterial3Api::class)

package com.portafolio.vientosdelsur.feature.auth.screens.signup.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.f776.core.ui.theme.VientosDelSurTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun ProgressScaffold(
    modifier: Modifier = Modifier,
    onGoBack: () -> Unit,
    onSkipStep: (() -> Unit)?,
    progress: () -> Float,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    Box(modifier = Modifier.width(64.dp)) {
                        IconButton(onClick = onGoBack) {
                            Icon(
                                imageVector = Icons.Default.ChevronLeft,
                                contentDescription = "Go to the previous step"
                            )
                        }
                    }
                },
                title = {
                    Row {
                        Spacer(modifier = Modifier.weight(1f))
                        LinearProgressIndicator(modifier = Modifier.weight(3f).height(12.dp), progress = progress)
                        Spacer(modifier = Modifier.weight(1f))
                    }
                },
                actions = {
                    Box(modifier = Modifier.width(64.dp)) {
                        this@CenterAlignedTopAppBar.AnimatedVisibility(onSkipStep != null) {
                            TextButton(onClick = onSkipStep!!) {
                                Text("Saltar")
                            }
                        }
                    }
                }
            )
        }
    ) {
        content(it)
    }
}

@Preview
@Composable
private fun ProgressScaffoldPreview() {
    VientosDelSurTheme {
        ProgressScaffold(
            onGoBack = {},
            content = {},
            onSkipStep = null,
            progress = { 1f }
        )
    }
}

@Preview
@Composable
private fun ProgressScaffoldSkipPreview() {
    VientosDelSurTheme {
        ProgressScaffold(
            onGoBack = {},
            content = {},
            onSkipStep = {},
            progress = { 0.5f }
        )
    }
}