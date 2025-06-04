package com.portafolio.vientosdelsur.feature.auth.screens.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.portafolio.vientosdelsur.domain.auth.Email
import com.portafolio.vientosdelsur.domain.auth.UserRepository
import com.portafolio.vientosdelsur.domain.auth.getFirstAndLastName
import com.portafolio.vientosdelsur.domain.employee.EmployeeRepository
import com.portafolio.vientosdelsur.feature.auth.screens.signup.data.ProfilePictureProvider
import com.portafolio.vientosdelsur.feature.auth.screens.signup.navigation.RegistrationRoute
import com.portafolio.vientosdelsur.feature.auth.screens.signup.steps.OccupationOption
import kotlinx.coroutines.flow.*
import kotlinx.datetime.*
import kotlin.time.Duration.Companion.seconds

internal class RegistrationFlowViewModel(
    private val employeeRepository: EmployeeRepository,
    private val profilePictureProvider: ProfilePictureProvider,
    userRepository: UserRepository
) : ViewModel() {

    init {
        addCloseable(profilePictureProvider)
    }

    var firstName by mutableStateOf("")
        private set

    var lastName by mutableStateOf("")
        private set

    val initialData = userRepository.currentUser
        .filterNotNull()
        .onEach { user ->
            val (first, last) = user.getFirstAndLastName()
            firstName = first
            lastName = last
            userProfilePicture = user.photoUrl?.let { UserProfilePicture.URL(it) } ?: UserProfilePicture.None
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(2.seconds), null)

    var userProfilePicture = profilePictureProvider.profilePicture

    var progress by mutableFloatStateOf(RegistrationRoute.Profile.progress)
        private set

    var dayOfWeek by mutableStateOf<DayOfWeek?>(null)
        private set

    var hireDate by mutableStateOf<LocalDate?>(null)
        private set

    var dayOff by mutableStateOf<DayOfWeek?>(null)
        private set

    var occupation by mutableStateOf<OccupationOption?>(null)
        private set

    fun onNavigationEvent(route: RegistrationRoute) {
        progress = route.progress
    }

    fun onDayOfWeekSelected(selectedDay: DayOfWeek?) {
        dayOfWeek = if (dayOfWeek == selectedDay) null else selectedDay
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

    fun onFirstNameChanged(name: String) {
        firstName = name
    }

    fun onLastNameChanged(name: String) {
        lastName = name
    }
}

internal val LocalDate.formatted: String
    get() {
        val day = dayOfMonth.toString().padStart(2, '0')
        val month = monthNumber.toString().padStart(2, '0')
        val year = year
        return "$day/$month/$year"
    }


internal data class EmployeeRegistrationData(
    val firstName: String,
    val lastName: String,
    val occupation: OccupationOption,
    val dayOff: DayOfWeek,
    val hireDate: LocalDate,
    // From User
    val email: Email,
    val userId: String,
    val photoUrl: String?
)

sealed interface UserProfilePicture {
    data class URL(val url: String) : UserProfilePicture
    data class Image(val image: ImageBitmap) : UserProfilePicture
    data object None : UserProfilePicture
}