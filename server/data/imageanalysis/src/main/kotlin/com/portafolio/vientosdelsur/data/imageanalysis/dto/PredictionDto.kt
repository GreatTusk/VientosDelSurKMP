package com.portafolio.vientosdelsur.data.imageanalysis.dto

import kotlinx.serialization.Serializable

@Serializable
data class PredictionResponse(
    val id: String,
    val project: String,
    val iteration: String,
    val created: String,
    val predictions: List<PredictionDto>,
)

@Serializable
data class PredictionDto(
    val probability: Double,
    val tagId: String,
    val tagName: String,
)
