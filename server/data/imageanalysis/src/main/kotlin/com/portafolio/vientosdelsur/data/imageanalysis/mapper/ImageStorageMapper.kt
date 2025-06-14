package com.portafolio.vientosdelsur.data.imageanalysis.mapper

import com.portafolio.vientosdelsur.core.database.entity.imageanalysis.ImageAnalysisEntity
import com.portafolio.vientosdelsur.data.room.mapper.toRoom
import com.portafolio.vientosdelsur.domain.imageanalysis.storage.ImageAnalysis

internal fun ImageAnalysisEntity.toImageAnalysis() = ImageAnalysis(
    id = id.value,
    room = room.toRoom(),
    updatedAt = uploadedAt,
    cleanProbability = cleanProbability,
    uncleanProbability = uncleanProbability,
    image = image.bytes
)
