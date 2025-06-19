package com.f776.japanesedictionary.data.imageanalysis.mapper

import com.f776.japanesedictionary.domain.imageanalysis.RoomAnalysis
import com.f776.japanesedictionary.domain.imageanalysis.RoomApprovalStatus
import com.portafolio.vientosdelsur.data.room.mapper.toRoom
import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.domain.employee.Occupation
import com.portafolio.vientosdelsur.network.BuildConfig
import com.portafolio.vientosdelsur.shared.dto.imageanalysis.ImageAnalysisDto

internal fun ImageAnalysisDto.toImageAnalysis() = RoomAnalysis(
    id = id,
    room = room.toRoom(),
    updatedAt = updatedAt,
    result = imageAnalysisResultDto.toImageAnalysisResult(),
    imageUrl = "${BuildConfig.BASE_URL}/$imageUrl",
    // TODO
    approvalStatus = RoomApprovalStatus.APPROVED,
    housekeeper = Employee(
        id = 1,
        firstName = "Flor",
        lastName = "Gonzales",
        occupation = Occupation.HOUSEKEEPER,
        userId = "emp-123456",
        email = "flow.gonzals@vientosdelsur.com",
        photoUrl = "https://example.com/photos/employee1.jpg",
        phoneNumber = "+1234567890",
        isEnabled = true
    )
)