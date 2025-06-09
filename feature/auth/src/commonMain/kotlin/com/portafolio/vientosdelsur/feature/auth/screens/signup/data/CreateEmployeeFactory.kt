package com.portafolio.vientosdelsur.feature.auth.screens.signup.data

import com.portafolio.vientosdelsur.domain.employee.CreateEmployee
import com.portafolio.vientosdelsur.domain.employee.HousekeeperRole
import com.portafolio.vientosdelsur.domain.employee.Occupation
import com.portafolio.vientosdelsur.domain.employee.UploadPhoto
import com.portafolio.vientosdelsur.feature.auth.screens.signup.Picture
import com.portafolio.vientosdelsur.feature.auth.screens.signup.steps.HousekeeperRoleOption
import com.portafolio.vientosdelsur.feature.auth.screens.signup.steps.OccupationOption
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate

internal object CreateEmployeeFactory {
    suspend fun fromUiState(
        firstName: String,
        lastName: String,
        userId: String,
        email: String,
        picture: Picture,
        hireDate: LocalDate?,
        dayOff: DayOfWeek?,
        occupationOption: OccupationOption?,
        housekeeperRole: HousekeeperRoleOption?,
    ): CreateEmployee {
        check(firstName.isNotBlank())
        check(lastName.isNotBlank())

        return CreateEmployee(
            firstName = firstName,
            lastName = lastName,
            occupation = checkNotNull(occupationOption).toOccupation(),
            userId = userId,
            email = email,
            uploadPhoto = picture.toUploadPhoto(),
            hireDate = checkNotNull(hireDate),
            dayOff = checkNotNull(dayOff),
            housekeeperRole = housekeeperRole?.toHousekeeperRole()
        )
    }

    private suspend fun Picture.toUploadPhoto(): UploadPhoto? = when (this) {
        is Picture.Image -> UploadPhoto.Raw(image.toByteArray())
        is Picture.URL -> UploadPhoto.URL(url)
        Picture.None -> null
    }

    private fun OccupationOption.toOccupation(): Occupation = when (this) {
        OccupationOption.HOUSEKEEPER -> Occupation.HOUSEKEEPER
        OccupationOption.SUPERVISOR -> Occupation.SUPERVISOR
        OccupationOption.ADMIN -> Occupation.ADMIN
    }

    private fun HousekeeperRoleOption.toHousekeeperRole() = when (this) {
        HousekeeperRoleOption.KITCHEN -> HousekeeperRole.KITCHEN
        HousekeeperRoleOption.KITCHEN_SUPPORT -> HousekeeperRole.KITCHEN_SUPPORT
        HousekeeperRoleOption.ON_CALL -> HousekeeperRole.ON_CALL
    }

}

