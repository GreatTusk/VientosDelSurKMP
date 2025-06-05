package com.portafolio.vientosdelsur.feature.auth.screens.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.f776.core.common.onSuccess
import com.portafolio.vientosdelsur.domain.auth.getFirstAndLastName
import com.portafolio.vientosdelsur.domain.employee.EmployeeRepository
import com.portafolio.vientosdelsur.feature.auth.navigation.Registration
import com.portafolio.vientosdelsur.feature.auth.screens.signup.data.CreateEmployeeFactory
import com.portafolio.vientosdelsur.feature.auth.screens.signup.data.ProfilePictureProvider
import com.portafolio.vientosdelsur.feature.auth.screens.signup.navigation.RegistrationRoute
import com.portafolio.vientosdelsur.feature.auth.screens.signup.steps.HousekeeperRoleOption
import com.portafolio.vientosdelsur.feature.auth.screens.signup.steps.OccupationOption
import kotlinx.coroutines.launch
import kotlinx.datetime.*

internal class RegistrationFlowViewModel(
    private val employeeRepository: EmployeeRepository,
    private val profilePictureProvider: ProfilePictureProvider,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val user = savedStateHandle.toRoute<Registration>()

    var firstName by mutableStateOf("")
        private set

    var lastName by mutableStateOf("")
        private set

    init {
        addCloseable(profilePictureProvider)

        user.run {
            val (first, last) = userName.getFirstAndLastName()
            firstName = first
            lastName = last

            profilePictureUrl?.let {
                profilePictureProvider.updateProfilePicture(ProfilePicture.URL(it))
            }
        }
    }

    var userProfilePicture = profilePictureProvider.profilePicture

    var progress by mutableFloatStateOf(RegistrationRoute.Profile.progress)
        private set

    var hireDate by mutableStateOf<LocalDate?>(null)
        private set

    var dayOff by mutableStateOf<DayOfWeek?>(null)
        private set

    var occupation by mutableStateOf<OccupationOption?>(null)
        private set

    var housekeeperRole by mutableStateOf<HousekeeperRoleOption?>(null)
        private set

    fun onNavigationEvent(route: RegistrationRoute) {
        progress = route.progress
    }

    fun onHireDateSelected(millis: Long?) {
        if (millis == null) {
            hireDate = null
            return
        }

        val instant = Instant.fromEpochMilliseconds(millis)
        val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault()).date
        hireDate = localDateTime
    }

    fun onDayOffSelected(dayOfWeek: DayOfWeek) {
        dayOff = if (dayOff == dayOfWeek) null else dayOfWeek
    }

    fun onOccupationSelected(selectedOccupation: OccupationOption) {
        occupation = if (occupation == selectedOccupation) null else selectedOccupation
    }

    fun onHousekeeperRoleSelected(housekeeperRoleOption: HousekeeperRoleOption) {
        housekeeperRole = if (housekeeperRole == housekeeperRoleOption) null else housekeeperRoleOption
    }

    fun onFirstNameChanged(name: String) {
        firstName = name
    }

    fun onLastNameChanged(name: String) {
        lastName = name
    }

    fun onSubmit() {
        viewModelScope.launch {
            val employee = CreateEmployeeFactory.fromUiState(
                firstName = firstName,
                lastName = lastName,
                userId = user.userId,
                email = user.email,
                profilePicture = userProfilePicture.value,
                hireDate = hireDate,
                dayOff = dayOff,
                occupationOption = occupation,
                housekeeperRole = housekeeperRole
            )
            employeeRepository.createEmployee(employee)
                .onSuccess {
                    println("Created successfully!!")
                }
        }
    }
}

internal val LocalDate.formatted: String
    get() {
        val day = dayOfMonth.toString().padStart(2, '0')
        val month = monthNumber.toString().padStart(2, '0')
        val year = year
        return "$day/$month/$year"
    }

sealed interface ProfilePicture {
    data class URL(val url: String) : ProfilePicture
    data class Image(val image: ImageBitmap) : ProfilePicture
    data object None : ProfilePicture
}