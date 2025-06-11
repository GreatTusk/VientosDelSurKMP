package com.portafolio.vientosdelsur.foryou.screens.foryou.navigation

import kotlinx.serialization.Serializable

@Serializable
data object ForYouNavigation

sealed interface OccupationRoute {
    @Serializable
    data object Loading : OccupationRoute

    @Serializable
    data object Housekeeper : OccupationRoute

    @Serializable
    data object Supervisor : OccupationRoute

    @Serializable
    data object Admin : OccupationRoute
}