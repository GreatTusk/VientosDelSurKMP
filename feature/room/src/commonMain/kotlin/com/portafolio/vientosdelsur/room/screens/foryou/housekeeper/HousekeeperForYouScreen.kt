@file:OptIn(ExperimentalMaterial3Api::class)

package com.portafolio.vientosdelsur.room.screens.foryou.housekeeper

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowWidthSizeClass
import com.f776.core.ui.theme.VientosDelSurTheme
import com.f776.japanesedictionary.core.resource.app_name
import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.domain.employee.EmployeeRole
import com.portafolio.vientosdelsur.room.screens.foryou.components.RoomStateCard
import com.portafolio.vientosdelsur.room.screens.foryou.housekeeper.model.RoomStateUi
import com.portafolio.vientosdelsur.room.screens.foryou.housekeeper.model.toRoomUi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import vientosdelsur.feature.room.generated.resources.*

@Composable
internal fun RoomScreenRoot(
    modifier: Modifier = Modifier,
    housekeeperForYouViewModel: HousekeeperForYouViewModel = koinInject(),
    employee: Employee
) {
    val uiState by housekeeperForYouViewModel.uiState.collectAsStateWithLifecycle()
//    val employee by housekeeperForYouViewModel.user.collectAsStateWithLifecycle()
    RoomScreen(modifier = modifier, rooms = uiState, employee = employee)
}

@Composable
private fun RoomScreen(modifier: Modifier = Modifier, rooms: List<RoomStateUi>, employee: Employee?) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    var selectedIndex by remember { mutableIntStateOf(-1) }

    val options = listOf(
        stringResource(Res.string.cleaning_guest),
        stringResource(Res.string.cleaning_checkout),
    )

    val filteredRooms by remember(selectedIndex, rooms) {
        derivedStateOf {
            when (selectedIndex) {
                0 -> rooms.filter { it.cleaningType == Res.string.cleaning_guest }
                1 -> rooms.filter { it.cleaningType == Res.string.cleaning_checkout }
                else -> rooms
            }
        }
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(com.f776.japanesedictionary.core.resource.Res.string.app_name))
                },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Localized description"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { },
                icon = { Icon(imageVector = Icons.Filled.CameraAlt, stringResource(Res.string.photo_fab_cd)) },
                text = { Text(text = stringResource(Res.string.photo_fab_message)) },
            )
        }
    ) { innerPadding ->
        val layoutDirection = LocalLayoutDirection.current
        val currentSize = currentWindowAdaptiveInfo()
        LazyVerticalGrid(
            columns = GridCells.Adaptive(200.dp),
            contentPadding = PaddingValues(
                start = innerPadding.calculateStartPadding(layoutDirection) + 16.dp,
                end = innerPadding.calculateEndPadding(layoutDirection) + 16.dp,
                top = innerPadding.calculateTopPadding() + 16.dp,
                bottom = innerPadding.calculateBottomPadding() + 16.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            forYouHeader(employee = employee)

            item(span = { GridItemSpan(maxLineSpan) }) {
                SingleChoiceSegmentedButtonRow(
                    modifier = Modifier.then(
                        if (currentSize.windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT)
                            Modifier
                        else Modifier.wrapContentWidth(
                            Alignment.Start
                        )
                    )
                ) {
                    options.forEachIndexed { index, label ->
                        SegmentedButton(
                            shape = SegmentedButtonDefaults.itemShape(
                                index = index,
                                count = options.size
                            ),
                            onClick = {
                                selectedIndex = if (selectedIndex == index) {
                                    -1
                                } else {
                                    index
                                }
                            },
                            selected = index == selectedIndex,
                            label = { Text(label) }
                        )
                    }
                }
            }
            items(items = filteredRooms, key = { it.id }) { room ->
                RoomStateCard(room)
            }
        }
    }
}

private fun LazyGridScope.forYouHeader(employee: Employee?) {
    item(span = { GridItemSpan(maxLineSpan) }) {
        employee?.let {
            Column {
                Text(
                    text = stringResource(Res.string.greeting, "${employee.firstName} ${employee.lastName}"),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(
                        when (employee.role) {
                            EmployeeRole.HOUSEKEEPER -> Res.string.housekeeper_room_header
                            EmployeeRole.SUPERVISOR -> Res.string.supervisor_header
                            EmployeeRole.ADMIN -> Res.string.admin_header
                        }
                    ),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}


@Composable
@Preview
private fun RoomScreenPreview() {
    VientosDelSurTheme {
        Surface {
            RoomScreen(
                rooms = SampleRoomStates.sampleRoomStates.map { it.toRoomUi() },
                employee = Employee(1, "Flow", "Gonzals", EmployeeRole.HOUSEKEEPER)
            )
        }
    }
}