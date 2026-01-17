package com.e_commerce.profile.model

data class ProfileUiState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val city: String = "",
    val postalCode: String = "",
    val address: String = "",
    val phoneNumber: String = ""
)