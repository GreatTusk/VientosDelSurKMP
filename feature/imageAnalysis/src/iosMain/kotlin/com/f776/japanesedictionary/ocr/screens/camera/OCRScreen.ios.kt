package com.f776.japanesedictionary.ocr.screens.camera

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal actual fun OCRScreen(
    modifier: Modifier,
    onNavigateUp: () -> Unit,
    onNavigateToTranslation: (String) -> Unit,
    onNavigateToDictionary: (String) -> Unit,
    ocrViewModel: OCRViewModel
) {
}