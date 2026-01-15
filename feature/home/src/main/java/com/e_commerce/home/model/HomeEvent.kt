package com.e_commerce.home.model

sealed interface HomeEvent {
    data class UpdateErrorMessage(val message: String) : HomeEvent
    data class UpdateSuccessMessage(val message: String) : HomeEvent
}