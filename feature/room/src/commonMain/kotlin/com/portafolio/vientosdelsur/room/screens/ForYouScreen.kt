package com.portafolio.vientosdelsur.room.screens

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
internal fun RoomScreenRoot(modifier: Modifier = Modifier) {
    RoomScreen()
}

@Composable
private fun RoomScreen(modifier: Modifier = Modifier) {
    Scaffold(modifier = modifier) { innerPadding ->
        Box(modifier = modifier.fillMaxSize().padding(innerPadding)) {
            Text("Hey from room")
        }
    }
}

@Composable
@Preview
private fun RoomScreenPreview() {
    VientosDelSurTheme {
        RoomScreenRoot()
    }
}