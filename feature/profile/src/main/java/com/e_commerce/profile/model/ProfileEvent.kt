package com.e_commerce.profile.model

sealed interface ProfileEvent {
    data object NavigateBack : ProfileEvent
}