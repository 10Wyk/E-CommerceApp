package com.e_commerce.profile.model

import com.e_commerce.shared.domain.model.Country
import com.e_commerce.shared.utils.RequestState

data class ProfileUiState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val city: String? = null,
    val postalCode: Int? = null,
    val address: String? = null,
    val phoneNumber: String? = null,
    val country: Country = Country.Serbia,
    val requestState: RequestState<Unit> = RequestState.Idle
)