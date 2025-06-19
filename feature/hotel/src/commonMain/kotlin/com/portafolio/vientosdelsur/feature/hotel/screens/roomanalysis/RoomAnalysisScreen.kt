@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3AdaptiveApi::class)

package com.portafolio.vientosdelsur.feature.hotel.screens.roomanalysis

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
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
import com.f776.core.ui.adaptive.ThreePaneScaffoldPredictiveBackHandler
import com.f776.core.ui.navigation.isDetailPaneVisible
import com.f776.core.ui.theme.VientosDelSurTheme
import com.f776.japanesedictionary.domain.imageanalysis.RoomAnalysis
import com.f776.japanesedictionary.domain.imageanalysis.ImageAnalysisResult
import com.f776.japanesedictionary.domain.imageanalysis.RoomApprovalStatus
import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.domain.employee.Occupation
import com.portafolio.vientosdelsur.domain.room.Room
import com.portafolio.vientosdelsur.domain.room.RoomType
import com.portafolio.vientosdelsur.feature.hotel.screens.roomanalysis.components.NoRoomSelected
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
    analyzedRooms: List<RoomAnalysis>,
    selectedImage: RoomAnalysis?,
    onImageSelected: (RoomAnalysis) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val navigator = rememberListDetailPaneScaffoldNavigator()
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Análisis de habitaciones", fontWeight = FontWeight.SemiBold) },
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    AnimatedVisibility(
                        visible = navigator.canNavigateBack(),
                        enter = scaleIn() + fadeIn(),
                        exit = scaleOut() + fadeOut()
                    ) {
                        IconButton(
                            onClick = {
                                scope.launch { navigator.navigateBack() }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = "Volver a la lista de habitaciones"
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                AnimatedVisibility(
                    visible = navigator.isDetailPaneVisible(),
                    enter = scaleIn() + fadeIn(),
                    exit = scaleOut() + fadeOut()
                ) {
                    FloatingActionButton(onClick = {}) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = "Aprobar habitación")
                    }
                }
                AnimatedVisibility(
                    visible = navigator.isDetailPaneVisible(),
                    enter = scaleIn() + fadeIn(),
                    exit = scaleOut() + fadeOut()
                ) {
                    FloatingActionButton(onClick = {}, containerColor = MaterialTheme.colorScheme.errorContainer) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Rechazar habitación")
                    }
                }
            }
        }
    ) { innerPadding ->

        ThreePaneScaffoldPredictiveBackHandler(navigator)

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
                        items(items = analyzedRooms, key = RoomAnalysis::id) { imageAnalysis ->
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
                        key(selectedImage.id) {
                            val zoomState = rememberZoomState()
                            Box(
                                modifier = Modifier.fillMaxSize().padding(innerPadding),
                                contentAlignment = Alignment.Center
                            ) {
                                AsyncImage(
                                    modifier = Modifier.fillMaxSize().zoomable(zoomState),
                                    model = selectedImage.imageUrl,
                                    contentDescription = "Limpieza de la habitación ${selectedImage.room.roomNumber}",
                                    contentScale = ContentScale.Fit
                                )
                            }
                        }
                    } else {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            NoRoomSelected()
                        }
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
                RoomAnalysis(
                    id = it,
                    room = Room(
                        id = 101,
                        roomNumber = "A-101",
                        roomType = RoomType.DOUBLE
                    ),
                    updatedAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                    result = ImageAnalysisResult.CLEAN, // assuming this enum exists
                    imageUrl = "https://example.com/images/room101.jpg",
                    approvalStatus = RoomApprovalStatus.APPROVED,
                    housekeeper = Employee(
                        id = 1,
                        firstName = "Flor",
                        lastName = "Gonzales",
                        occupation = Occupation.HOUSEKEEPER,
                        userId = "emp-123456",
                        email = "flow.gonzals@vientosdelsur.com",
                        photoUrl = "https://example.com/photos/employee1.jpg",
                        phoneNumber = "+1234567890",
                        isEnabled = true
                    )
                )
            },
            selectedImage = null,
            onImageSelected = {}
        )
    }
}

