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
    return drawConclusion(cleanConfidence)
}

internal fun drawConclusion(cleanConfidence: Double) = when {
    cleanConfidence >= 0.8 -> AnalysisConclusion.CLEAN
    cleanConfidence >= 0.6 -> AnalysisConclusion.SLIGHTLY_DIRTY
    cleanConfidence >= 0.4 -> AnalysisConclusion.MODERATELY_DIRTY
    cleanConfidence >= 0.2 -> AnalysisConclusion.VERY_DIRTY
    else -> AnalysisConclusion.EXTREMELY_DIRTY
}