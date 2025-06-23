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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.f776.core.ui.theme.VientosDelSurTheme
import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.domain.employee.Occupation
import com.portafolio.vientosdelsur.foryou.screens.foryou.supervisor.ui.displayName
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun EmployeeCard(
    modifier: Modifier = Modifier,
    employee: Employee,
) {
    ElevatedCard(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Profile Picture
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                employee.photoUrl?.let {
                    AsyncImage(
                        model = it,
                        contentDescription = employee.fullName,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } ?: Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Default profile picture",
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Employee Information
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = employee.fullName,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = employee.occupation.displayName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = employee.phoneNumber,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
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