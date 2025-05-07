package com.portafolio.vientosdelsur.foryou.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.f776.core.ui.theme.VientosDelSurTheme
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
internal fun ForYouScreenRoot(modifier: Modifier = Modifier) {
    ForYouScreen()
}

@Composable
private fun ForYouScreen(modifier: Modifier = Modifier) {
    Scaffold(modifier = modifier) { innerPadding ->
        Box(modifier = modifier.fillMaxSize().padding(innerPadding)) {
            Text("Hey from For You")
        }
    }
}

@Composable
@Preview
private fun ForYouScreenPreview() {
    VientosDelSurTheme {
        ForYouScreenRoot()
    }
}