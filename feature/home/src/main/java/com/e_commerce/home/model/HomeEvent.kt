package com.e_commerce.home.model

import com.e_commerce.shared.presentation.navigation.Screen

sealed interface HomeEvent {
    data class UpdateErrorMessage(val message: String) : HomeEvent
    data class UpdateSuccessMessage(val message: String) : HomeEvent
    data class NavigateToScreen(val screen: Screen) : HomeEvent
}