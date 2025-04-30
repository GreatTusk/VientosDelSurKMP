@file:OptIn(ExperimentalResourceApi::class)

package com.f776.japanesedictionary.ocr.screens.fileSelector

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.decodeToImageBitmap
import java.io.File
import java.io.FileInputStream

internal class OCRViewModel : ViewModel() {
    var imagePainter by mutableStateOf<Painter?>(null)
        private set

    fun onAction(action: OCRAction) {
        when (action) {
            is OCRAction.OnImageSelected -> viewModelScope.launch {
                imagePainter = loadImageFromFile(action.image)
            }
        }
    }

    // should be moved to the data layer?
    private suspend fun loadImageFromFile(file: File): Painter {
        return BitmapPainter(withContext(Dispatchers.IO) {
            FileInputStream(file).use { it.readAllBytes().decodeToImageBitmap() }
        })
    }
}