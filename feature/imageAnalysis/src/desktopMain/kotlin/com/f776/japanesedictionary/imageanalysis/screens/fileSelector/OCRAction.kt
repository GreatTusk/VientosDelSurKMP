package com.f776.japanesedictionary.imageanalysis.screens.fileSelector

import java.io.File

internal sealed interface OCRAction {
    data class OnImageSelected(val image: File) : OCRAction
}