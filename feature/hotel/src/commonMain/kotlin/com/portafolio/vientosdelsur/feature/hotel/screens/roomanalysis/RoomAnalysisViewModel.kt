package com.portafolio.vientosdelsur.feature.hotel.screens.roomanalysis

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f776.core.common.onError
import com.f776.core.common.onSuccess
import com.f776.core.common.takeOrNull
import com.f776.japanesedictionary.domain.imageanalysis.ImageAnalysisRepository
import com.f776.japanesedictionary.domain.imageanalysis.RoomAnalysis
import com.f776.japanesedictionary.domain.imageanalysis.RoomApprovalStatus
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import vientosdelsur.feature.hotel.generated.resources.Res
import vientosdelsur.feature.hotel.generated.resources.approved_successfully
import vientosdelsur.feature.hotel.generated.resources.rejected_successfully
import vientosdelsur.feature.hotel.generated.resources.revision_error
import kotlin.time.Duration.Companion.seconds

internal class RoomAnalysisViewModel(
    private val imageAnalysisRepository: ImageAnalysisRepository
) : ViewModel() {

    private val _eventChannel = Channel<StringResource>()
    val eventChannel = _eventChannel.receiveAsFlow()

    private val _analyzedRooms = flow { emit(imageAnalysisRepository.getRoomsSubmittedToday().takeOrNull()) }
        .filterNotNull()

    val analyzedRooms = _analyzedRooms
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5.seconds),
            initialValue = emptyList()
        )

    private val _selectedRoomImage = MutableStateFlow<RoomAnalysis?>(null)
    val selectedRoomImage = _selectedRoomImage.asStateFlow()

    fun onImageSelected(roomAnalysis: RoomAnalysis) {
        if (roomAnalysis.id == _selectedRoomImage.value?.id) {
            return
        }
        _selectedRoomImage.update { roomAnalysis }
    }

    fun onRoomCleaningRevision(roomAnalysisId: Int, roomApprovalStatus: RoomApprovalStatus) {
        viewModelScope.launch {
            imageAnalysisRepository.reviewRoomCleaning(roomAnalysisId, roomApprovalStatus)
                .onSuccess {
                    _eventChannel.send(
                        when (roomApprovalStatus) {
                            RoomApprovalStatus.APPROVED -> Res.string.approved_successfully
                            RoomApprovalStatus.REJECTED -> Res.string.rejected_successfully
                            RoomApprovalStatus.PENDING -> error("Impossible state")
                        }
                    )
                }
                .onError {
                    _eventChannel.send(Res.string.revision_error)
                }
        }
    }
}