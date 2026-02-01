package com.e_commerce.profile.model

import com.e_commerce.shared.domain.model.Country
import com.e_commerce.shared.domain.model.Customer
import com.e_commerce.shared.utils.RequestState

data class ProfileUiState(
    val customer: Customer? = null,
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val city: String? = null,
    val postalCode: Int? = null,
    val address: String? = null,
    val phoneNumber: String? = null,
    val country: Country = Country.Serbia,
    val profileValidity: ProfileValidity = ProfileValidity(),
    val requestState: RequestState<Unit> = RequestState.Idle
)

data class ProfileValidity(
    val isFirstNameValid: Boolean = true,
    val isLastNameValid: Boolean = true
) {
    fun isValid() = isLastNameValid && isFirstNameValid
}