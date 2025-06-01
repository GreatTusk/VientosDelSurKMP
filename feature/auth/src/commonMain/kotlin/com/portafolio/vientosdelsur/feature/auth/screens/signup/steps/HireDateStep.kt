@file:OptIn(ExperimentalMaterial3Api::class)

package com.portafolio.vientosdelsur.feature.auth.screens.signup.steps

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Event
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.f776.core.ui.theme.VientosDelSurTheme
import com.portafolio.vientosdelsur.feature.auth.screens.signup.components.ProgressScaffold
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview

internal fun convertMillisToDate(millis: Long): String {
    val instant = Instant.fromEpochMilliseconds(millis)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    val day = localDateTime.dayOfMonth.toString().padStart(2, '0')
    val month = localDateTime.monthNumber.toString().padStart(2, '0')
    val year = localDateTime.year
    return "$day/$month/$year"
}

@Composable
internal fun HireDateStep(modifier: Modifier = Modifier, onContinue: () -> Unit) {
    var showDatePickerDialog by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    var selectedDateMillisForDisplay by remember { mutableStateOf<Long?>(null) }

    Box(modifier = modifier.fillMaxSize().padding(horizontal = 32.dp)) {
        Text(
            text = "¿Cuál es tu fecha de contratación?",
            modifier = Modifier.align(Alignment.TopCenter).padding(top = 16.dp),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = selectedDateMillisForDisplay?.let { convertMillisToDate(it) } ?: "",
                onValueChange = {  },
                modifier = Modifier
                    .fillMaxWidth()
                    .pointerInput(Unit) {
                        awaitEachGesture {
                            awaitFirstDown(pass = PointerEventPass.Initial)
                            val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                            if (upEvent != null) {
                                showDatePickerDialog = true
                            }
                        }
                    },
                readOnly = true,
                label = { Text("Fecha de contratación") },
                placeholder = { Text("Selecciona una fecha") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Event,
                        contentDescription = "Icono de fecha"
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Seleccionar fecha"
                    )
                }
            )
        }

        if (showDatePickerDialog) {
            DatePickerDialog(
                onDismissRequest = { showDatePickerDialog = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            selectedDateMillisForDisplay = datePickerState.selectedDateMillis
                            showDatePickerDialog = false
                        }
                    ) {
                        Text("Aceptar")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showDatePickerDialog = false }
                    ) {
                        Text("Cancelar")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        Button(
            onClick = onContinue,
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 24.dp)
                .align(Alignment.BottomCenter),
            enabled = selectedDateMillisForDisplay != null
        ) {
            Text("Continuar")
        }
    }
}

@Preview
@Composable
private fun HireDateStepPreview() {
    VientosDelSurTheme {
        ProgressScaffold(
            onGoBack = {},
            onSkipStep = null,
            progress = { 0.1f },
            content = {
                HireDateStep(
                    onContinue = {},
                    modifier = Modifier.padding(it)
                )
            }
        )
    }
}