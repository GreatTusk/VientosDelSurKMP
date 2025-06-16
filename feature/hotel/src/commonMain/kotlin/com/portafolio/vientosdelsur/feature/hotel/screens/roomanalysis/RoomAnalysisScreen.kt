@file:OptIn(ExperimentalMaterial3Api::class)

package com.portafolio.vientosdelsur.feature.hotel.screens.roomanalysis

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.f776.core.ui.theme.VientosDelSurTheme
import com.f776.japanesedictionary.domain.imageanalysis.ImageAnalysis
import com.f776.japanesedictionary.domain.imageanalysis.ImageAnalysisResult
import com.portafolio.vientosdelsur.domain.room.Room
import com.portafolio.vientosdelsur.domain.room.RoomType
import com.portafolio.vientosdelsur.feature.hotel.screens.roomanalysis.components.RoomAnalysisCard
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun RoomAnalysisScreenRoot(
    modifier: Modifier = Modifier,
    roomAnalysisViewModel: RoomAnalysisViewModel = koinViewModel()
) {
    val analyzedRooms by roomAnalysisViewModel.analyzedRooms.collectAsStateWithLifecycle()

    RoomAnalysisScreen(modifier = modifier, analyzedRooms = analyzedRooms)
}

@Composable
private fun RoomAnalysisScreen(modifier: Modifier = Modifier, analyzedRooms: List<ImageAnalysis>) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Análisis de habitaciones", fontWeight = FontWeight.SemiBold) },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        val localLayout = LocalLayoutDirection.current
        val padding = PaddingValues(
            start = innerPadding.calculateStartPadding(localLayout) + 8.dp,
            end = innerPadding.calculateEndPadding(localLayout) + 8.dp,
            top = innerPadding.calculateTopPadding() + 8.dp,
            bottom = innerPadding.calculateBottomPadding() + 8.dp
        )
        LazyVerticalGrid(
            columns = GridCells.Adaptive(150.dp),
            contentPadding = padding,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = analyzedRooms, key = ImageAnalysis::id) { imageAnalysis ->
                RoomAnalysisCard(roomAnalysis = imageAnalysis)
            }
        }
    }
}

@Preview
@Composable
private fun RoomAnalysisScreenPreview() {
    VientosDelSurTheme {
        RoomAnalysisScreen(analyzedRooms = List(10) {
            ImageAnalysis(
                id = it,
                room = Room(
                    id = 101,
                    roomNumber = "A-101",
                    roomType = RoomType.DOUBLE
                ),
                updatedAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                result = ImageAnalysisResult.CLEAN, // assuming this enum exists
                imageUrl = "https://example.com/images/room101.jpg"
            )
        })
    }
}

