@file:OptIn(ExperimentalMaterial3Api::class)

package com.portafolio.vientosdelsur.feature.shift.screens.admin

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.f776.core.ui.theme.VientosDelSurTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun ShiftReviewerScreenRoot(modifier: Modifier = Modifier) {

}

@Composable
private fun ShiftReviewerScreen(modifier: Modifier = Modifier) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val layoutDirection = LocalLayoutDirection.current

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Tus turnos",
                        fontWeight = FontWeight.SemiBold
                    )
                },
                scrollBehavior = scrollBehavior
            )
        },
    ) { innerPadding ->
        LazyColumn(
            contentPadding = PaddingValues(
                start = innerPadding.calculateStartPadding(layoutDirection) + 16.dp,
                end = innerPadding.calculateEndPadding(layoutDirection) + 16.dp,
            )
        ) {

        }
    }
}

@Preview
@Composable
private fun ShiftReviewerScreenPreview() {
    VientosDelSurTheme {
        ShiftReviewerScreen()
    }
}