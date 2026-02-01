package com.e_commerce.profile.model

sealed interface ProfileEvent {
    data object NavigateBack : ProfileEvent
    data class UpdateErrorMessage(val message: String) : ProfileEvent
    data class UpdateSuccessMessage(val message: String) : ProfileEvent
}