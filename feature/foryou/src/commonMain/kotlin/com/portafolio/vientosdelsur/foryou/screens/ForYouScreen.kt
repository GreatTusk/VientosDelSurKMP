@file:OptIn(ExperimentalMaterial3Api::class)

package com.portafolio.vientosdelsur.foryou.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.f776.core.ui.theme.VientosDelSurTheme
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
internal fun ForYouScreenRoot(modifier: Modifier = Modifier) {
    ForYouScreen(modifier = modifier)
}

@Composable
private fun ForYouScreen(modifier: Modifier = Modifier) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier,
        topBar = {
            MediumTopAppBar(
                title = {
                    Text("For you")
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        LazyVerticalGrid(
            modifier = modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            columns = GridCells.Fixed(2),
            contentPadding = innerPadding
        ) {
            item {

            }
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