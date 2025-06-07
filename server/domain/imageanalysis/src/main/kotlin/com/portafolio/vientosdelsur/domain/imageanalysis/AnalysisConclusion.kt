package com.portafolio.vientosdelsur.domain.imageanalysis

enum class AnalysisConclusion {
    CLEAN,
    SLIGHTLY_DIRTY,
    MODERATELY_DIRTY,
    VERY_DIRTY,
    EXTREMELY_DIRTY
}

fun List<ImageAnalysisResult>.drawAnalysisConclusion(): AnalysisConclusion {
    val cleanConfidence = first { it.tagName == ResultTag.CLEAN }.probability

    return when {
        cleanConfidence >= 0.8f -> AnalysisConclusion.CLEAN
        cleanConfidence >= 0.6f -> AnalysisConclusion.SLIGHTLY_DIRTY
        cleanConfidence >= 0.4f -> AnalysisConclusion.MODERATELY_DIRTY
        cleanConfidence >= 0.2f -> AnalysisConclusion.VERY_DIRTY
        else -> AnalysisConclusion.EXTREMELY_DIRTY
    }
}