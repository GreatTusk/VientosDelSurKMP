package com.portafolio.vientosdelsur.foryou.screens.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.domain.employee.EmployeeRole
import kotlinx.datetime.*
import kotlinx.datetime.format.DateTimeFormat
import org.jetbrains.compose.resources.stringResource
import vientosdelsur.feature.foryou.generated.resources.*
import vientosdelsur.feature.foryou.generated.resources.Res
import vientosdelsur.feature.foryou.generated.resources.greeting
import vientosdelsur.feature.foryou.generated.resources.housekeeper_room_header
import vientosdelsur.feature.foryou.generated.resources.supervisor_header


internal fun LazyGridScope.forYouHeader(employee: Employee?) {
    item(span = { GridItemSpan(maxLineSpan) }) {
        employee?.let {
            Column {
                Text(
                    text = stringResource(Res.string.greeting, "${employee.firstName} ${employee.lastName}"),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
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
                    Text(
                        text = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                            .format(localeDateFormatter),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
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