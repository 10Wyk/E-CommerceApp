package com.e_commerce.auth.model

sealed interface AuthEvent {
    data class UpdateErrorMessage(val message: String) : AuthEvent
    data class UpdateSuccessMessage(val message: String) : AuthEvent
}