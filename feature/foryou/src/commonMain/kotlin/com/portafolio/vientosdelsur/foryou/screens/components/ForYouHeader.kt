package com.portafolio.vientosdelsur.foryou.screens.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.f776.core.ui.theme.VientosDelSurTheme
import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.domain.employee.Occupation
import kotlinx.datetime.*
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import vientosdelsur.feature.foryou.generated.resources.*
import vientosdelsur.feature.foryou.generated.resources.Res
import vientosdelsur.feature.foryou.generated.resources.greeting
import vientosdelsur.feature.foryou.generated.resources.housekeeper_room_header
import vientosdelsur.feature.foryou.generated.resources.supervisor_header


internal fun LazyGridScope.forYouHeader(employee: Employee) {
    item(span = { GridItemSpan(maxLineSpan) }) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(Res.string.greeting, "${employee.firstName} ${employee.lastName}"),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.SemiBold
                )
                employee.photoUrl?.let {
                    AsyncImage(
                        modifier = Modifier.size(48.dp).clip(CircleShape),
                        model = it,
                        contentDescription = "Profile picture",
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = stringResource(
                        when (employee.occupation) {
                            Occupation.HOUSEKEEPER -> Res.string.housekeeper_room_header
                            Occupation.SUPERVISOR -> Res.string.supervisor_header
                            Occupation.ADMIN -> Res.string.admin_header
                        }
                    ),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                        .format(localeDateFormatter),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

private val localeDateFormatter = LocalDateTime.Format {
    dayOfMonth()
    chars("/")
    monthNumber()
    chars("/")
    year()
}


@Preview
@Composable
private fun ForYouHeaderPreview() {
    VientosDelSurTheme {
        Surface {
            LazyVerticalGrid(columns = GridCells.Fixed(1)) {
                forYouHeader(
                    employee = Employee(
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
            }
        }
    }
}