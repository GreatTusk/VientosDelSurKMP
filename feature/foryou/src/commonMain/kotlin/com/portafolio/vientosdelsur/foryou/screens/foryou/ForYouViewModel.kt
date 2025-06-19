package com.portafolio.vientosdelsur.foryou.screens.foryou

import androidx.lifecycle.ViewModel
import com.portafolio.vientosdelsur.domain.auth.UserRepository

internal class ForYouViewModel(
    userRepository: UserRepository
) : ViewModel() {
    val employee = userRepository.currentUser
}