package com.portafolio.vientosdelsur.foryou.screens.foryou.supervisor.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.f776.core.ui.theme.VientosDelSurTheme
import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.domain.employee.Occupation
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun EmployeeCard(modifier: Modifier = Modifier, employee: Employee) {
    ElevatedCard(modifier = modifier) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column {
                Text(
                    text = employee.occupation.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = employee.fullName,
                    style = MaterialTheme.typography.titleLarge
                )
                AssistChip(
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Phone, contentDescription = null)
                    },
                    label = {
                        Text(text = employee.phoneNumber, style = MaterialTheme.typography.bodyMedium)
                    },
                    onClick = {}
                )
            }
            Box {
                employee.photoUrl?.let {
                    AsyncImage(
                        modifier = Modifier.size(96.dp).clip(CircleShape),
                        model = it,
                        contentDescription = "Profile picture",
                        contentScale = ContentScale.Crop
                    )
                } ?: Icon(
                    imageVector = Icons.Default.AccountCircle,
                    modifier = Modifier.size(96.dp).align(Alignment.Center),
                    contentDescription = "Profile picture"
                )
            }
        }
    }
}

@Preview
@Composable
private fun EmployeeCardPreview() {
    VientosDelSurTheme {
        Surface {
            EmployeeCard(
                modifier = Modifier.padding(24.dp),
                employee = Employee(
                    id = 1,
                    firstName = "Flor",
                    lastName = "Gonzales",
                    occupation = Occupation.SUPERVISOR,
                    userId = "emp-123456",
                    email = "flow.gonzals@vientosdelsur.com",
                    photoUrl = null,
                    phoneNumber = "+1234567890",
                    isEnabled = true
                )
            )
        }
    }
}