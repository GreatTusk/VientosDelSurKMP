package com.portafolio.vientosdelsur.feature.auth.screens.signup.data

import com.portafolio.vientosdelsur.domain.employee.CreateEmployee
import com.portafolio.vientosdelsur.domain.employee.Occupation
import com.portafolio.vientosdelsur.domain.employee.UploadPhoto
import com.portafolio.vientosdelsur.feature.auth.screens.signup.ProfilePicture
import com.portafolio.vientosdelsur.feature.auth.screens.signup.steps.OccupationOption
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate

internal object CreateEmployeeFactory {
    fun fromUiState(
        firstName: String,
        lastName: String,
        userId: String,
        email: String,
        phoneNumber: String?,
        profilePicture: ProfilePicture,
        hireDate: LocalDate,
        dayOff: DayOfWeek,
        occupationOption: OccupationOption,
    ) {
        CreateEmployee(
            firstName = firstName,
            lastName = lastName,
            occupation = occupationOption.toOccupation(),
            userId = userId,
            email = email,
            phoneNumber = phoneNumber,
            uploadPhoto = profilePicture.toUploadPhoto(),
            hireDate = hireDate,
            dayOff = dayOff
        )
    }

    private fun ProfilePicture.toUploadPhoto(): UploadPhoto? {
        return when (this) {
            is ProfilePicture.Image -> UploadPhoto.Raw(image.toByteArray())
            is ProfilePicture.URL -> UploadPhoto.URL(url)
            ProfilePicture.None -> null
        }
    }

    private fun OccupationOption.toOccupation(): Occupation {
        return when (this) {
            OccupationOption.HOUSEKEEPER -> Occupation.HOUSEKEEPER
            OccupationOption.SUPERVISOR -> Occupation.SUPERVISOR
            OccupationOption.ADMIN -> Occupation.ADMIN
        }
    }
}

