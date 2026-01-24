package com.e_commerce.profile.model

import com.e_commerce.shared.domain.model.Country

sealed interface ProfileAction {
    data class OnChangeFirstName(val firstName: String) : ProfileAction
    data class OnChangeLastName(val lastName: String) : ProfileAction
    data class OnChangeCity(val city: String) : ProfileAction
    data class OnChangePostalCode(val code: String) : ProfileAction
    data class OnChangeAddress(val address: String) : ProfileAction
    data class OnChangePhoneNumber(val phoneNumber: String) : ProfileAction
    data class OnPickCountry(val country: Country) : ProfileAction
    data object OnNavigateBackClick : ProfileAction
}