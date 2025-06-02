package com.portafolio.vientosdelsur.feature.auth.screens.signup.steps

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.f776.core.ui.theme.VientosDelSurTheme
import com.portafolio.vientosdelsur.feature.auth.screens.signup.components.ProgressScaffold
import kotlinx.datetime.DayOfWeek
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import vientosdelsur.feature.auth.generated.resources.*

private val DayOfWeek.displayName: StringResource
    get() {
        return when (this) {
            DayOfWeek.MONDAY -> Res.string.monday
            DayOfWeek.TUESDAY -> Res.string.tuesday
            DayOfWeek.WEDNESDAY -> Res.string.wednesday
            DayOfWeek.THURSDAY -> Res.string.thursday
            DayOfWeek.FRIDAY -> Res.string.friday
            DayOfWeek.SATURDAY -> Res.string.saturday
            DayOfWeek.SUNDAY -> Res.string.sunday
            else -> error("Expected enums must have an else branch to be considered exhaustive")
        }
    }

@Composable
internal fun DayOffStep(
    modifier: Modifier = Modifier,
    onContinue: () -> Unit,
    dayOfWeek: DayOfWeek?,
    onDayOfWeekSelected: (DayOfWeek) -> Unit,
) {
    Box(modifier = modifier.fillMaxSize().padding(horizontal = 32.dp)) {
        Text(
            text = "¿Qué día tienes libre?",
            modifier = Modifier.align(Alignment.TopCenter).padding(top = 16.dp),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )

        Column(
            modifier = Modifier.align(Alignment.Center).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                maxItemsInEachRow = 3
            ) {
                DayOfWeek.entries.forEach { day ->
                    FilterChip(
                        selected = dayOfWeek == day,
                        onClick = { onDayOfWeekSelected(day) },
                        label = {
                            Text(
                                text = stringResource(day.displayName),
                                style = MaterialTheme.typography.labelLarge,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        },
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier.height(48.dp)
                    )
                }
            }
        }

        Button(
            onClick = onContinue,
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 24.dp)
                .align(Alignment.BottomCenter),
            enabled = dayOfWeek != null
        ) {
            Text("Continuar")
        }
    }
}

@Preview
@Composable
private fun DayOffStepPreview() {
    VientosDelSurTheme {
        ProgressScaffold(
            onGoBack = {},
            onSkipStep = null,
            progress = { 0.1f },
            content = {
                DayOffStep(
                    onContinue = {},
                    modifier = Modifier.padding(it),
                    dayOfWeek = null,
                    onDayOfWeekSelected = {}
                )
            }
        )
    }
}