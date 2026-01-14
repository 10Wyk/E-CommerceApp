package com.e_commerce.home.model

sealed interface HomeEvent {
    data object NavigateToAuth : HomeEvent
}