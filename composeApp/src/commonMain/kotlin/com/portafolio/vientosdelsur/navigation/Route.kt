package com.portafolio.vientosdelsur.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object TopLevel: Route
    @Serializable
    data object Onboarding: Route
}