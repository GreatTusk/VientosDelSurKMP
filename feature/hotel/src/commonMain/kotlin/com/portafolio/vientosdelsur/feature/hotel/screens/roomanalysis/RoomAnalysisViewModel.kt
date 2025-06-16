package com.portafolio.vientosdelsur.feature.hotel.screens.roomanalysis

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f776.core.common.takeOrNull
import com.f776.japanesedictionary.domain.imageanalysis.ImageAnalysis
import com.f776.japanesedictionary.domain.imageanalysis.ImageAnalysisRepository
import kotlinx.coroutines.flow.*
import kotlin.time.Duration.Companion.seconds

internal class RoomAnalysisViewModel(
    private val imageAnalysisRepository: ImageAnalysisRepository
) : ViewModel() {

    private val _analyzedRooms = flow { emit(imageAnalysisRepository.getRoomsSubmittedToday().takeOrNull()) }
        .filterNotNull()

    val analyzedRooms = _analyzedRooms
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5.seconds),
            initialValue = emptyList()
        )

    private val _selectedRoomImage = MutableStateFlow<ImageAnalysis?>(null)
    val selectedRoomImage = _selectedRoomImage.asStateFlow()

    fun onImageSelected(imageAnalysis: ImageAnalysis) {
        if (imageAnalysis.id == _selectedRoomImage.value?.id) {
            return
        }
        _selectedRoomImage.update { imageAnalysis }
    }
}