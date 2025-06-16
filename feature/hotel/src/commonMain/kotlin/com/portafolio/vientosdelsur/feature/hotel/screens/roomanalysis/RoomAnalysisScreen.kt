@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3AdaptiveApi::class)

package com.portafolio.vientosdelsur.feature.hotel.screens.roomanalysis

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.f776.core.ui.theme.VientosDelSurTheme
import com.f776.japanesedictionary.domain.imageanalysis.ImageAnalysis
import com.f776.japanesedictionary.domain.imageanalysis.ImageAnalysisResult
import com.portafolio.vientosdelsur.domain.room.Room
import com.portafolio.vientosdelsur.domain.room.RoomType
import com.portafolio.vientosdelsur.feature.hotel.screens.roomanalysis.components.RoomAnalysisCard
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun RoomAnalysisScreenRoot(
    modifier: Modifier = Modifier,
    roomAnalysisViewModel: RoomAnalysisViewModel = koinViewModel()
) {
    val analyzedRooms by roomAnalysisViewModel.analyzedRooms.collectAsStateWithLifecycle()
    val selectedImage by roomAnalysisViewModel.selectedRoomImage.collectAsStateWithLifecycle()

    RoomAnalysisScreen(
        modifier = modifier,
        analyzedRooms = analyzedRooms,
        selectedImage = selectedImage,
        onImageSelected = roomAnalysisViewModel::onImageSelected
    )
}

@Composable
private fun RoomAnalysisScreen(
    modifier: Modifier = Modifier,
    analyzedRooms: List<ImageAnalysis>,
    selectedImage: ImageAnalysis?,
    onImageSelected: (ImageAnalysis) -> Unit
) {
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
        val navigator = rememberListDetailPaneScaffoldNavigator()
        val scope = rememberCoroutineScope()
        ListDetailPaneScaffold(
            directive = navigator.scaffoldDirective,
            value = navigator.scaffoldValue,
            listPane = {
                AnimatedPane {
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
                            RoomAnalysisCard(
                                roomAnalysis = imageAnalysis,
                                onImageSelected = {
                                    onImageSelected(imageAnalysis)
                                    scope.launch {
                                        navigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
                                    }
                                }
                            )
                        }
                    }
                }
            },
            detailPane = {
                AnimatedPane {
                    if (selectedImage != null) {
                        val zoomState = rememberZoomState()
                        Box(modifier = Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                            AsyncImage(
                                modifier = Modifier.fillMaxSize().zoomable(zoomState),
                                model = selectedImage.imageUrl,
                                contentDescription = "Limpieza de la habitación ${selectedImage.room.roomNumber}",
                                contentScale = ContentScale.Fit
                            )
                        }
                    } else {
                        // No image selected
                    }
                }
            }
        )


    }
}

@Preview
@Composable
private fun RoomAnalysisScreenPreview() {
    VientosDelSurTheme {
        RoomAnalysisScreen(
            analyzedRooms = List(10) {
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
            },
            selectedImage = null,
            onImageSelected = {}
        )
    }
}

