@file:OptIn(ExperimentalMaterial3Api::class)

package com.portafolio.vientosdelsur.foryou.screens.foryou.supervisor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.f776.core.ui.theme.VientosDelSurTheme
import com.f776.japanesedictionary.core.resource.app_name
import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.domain.employee.Occupation
import com.portafolio.vientosdelsur.foryou.screens.components.forYouHeader
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun SupervisorForYouScreenRoot(
    modifier: Modifier = Modifier,
    employee: Employee
) {
    SupervisorForYouScreen(modifier = modifier, employee = employee)
}

@Composable
private fun SupervisorForYouScreen(
    modifier: Modifier = Modifier,
    employee: Employee
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(com.f776.japanesedictionary.core.resource.Res.string.app_name),
                        fontWeight = FontWeight.SemiBold
                    )
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
                onClick = { /* supervisor action */ },
                icon = { Icon(Icons.Default.ImageSearch, contentDescription = "Supervisor action") },
                text = { Text("Revisar") },
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

        }
    }
}

@Composable
@Preview
private fun RoomScreenPreview() {
    VientosDelSurTheme {
        Surface {
            SupervisorForYouScreen(
                employee = Employee(
                    id = 1,
                    firstName = "Flor",
                    lastName = "Gonzales",
                    occupation = Occupation.SUPERVISOR,
                    userId = "emp-123456",
                    email = "flow.gonzals@vientosdelsur.com",
                    photoUrl = "https://example.com/photos/employee1.jpg",
                    phoneNumber = "+1234567890",
                    isEnabled = true
                ),
            )
        }
    }
}