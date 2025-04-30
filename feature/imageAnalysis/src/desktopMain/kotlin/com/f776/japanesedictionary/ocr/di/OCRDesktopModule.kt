package com.f776.japanesedictionary.ocr.di

import com.f776.japanesedictionary.ocr.screens.fileSelector.OCRViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val OCRDesktopModule = module {
    viewModelOf(::OCRViewModel)
}