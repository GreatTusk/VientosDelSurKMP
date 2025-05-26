package com.portafolio.vientosdelsur.shared.dto.employee

import kotlinx.serialization.Serializable

@Serializable
data class HousekeeperDto(
    val role: HousekeeperRoleDto,
    val preferredFloor: Int?
)

@Serializable
enum class HousekeeperRoleDto{
    Kitchen,
    KitchenSupport,
    OnCall
}

