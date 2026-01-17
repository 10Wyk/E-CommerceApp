package com.e_commerce.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.e_commerce.profile.model.ProfileAction
import com.e_commerce.profile.model.ProfileUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(ProfileUiState())
    val state = _state.asStateFlow()

    fun actonHandler(action: ProfileAction) {
        when (action) {
            is ProfileAction.OnChangeAddress -> changeAddress(action.address)
            is ProfileAction.OnChangeCity -> changeCity(action.city)
            is ProfileAction.OnChangeFirstName -> changeFirstName(action.firstName)
            is ProfileAction.OnChangeLastName -> changeLastName(action.lastName)
            is ProfileAction.OnChangePostalCode -> changePostalCode(action.code)
            is ProfileAction.OnChangePhoneNumber -> changePhoneNumber(action.phoneNumber)
        }
    }

    private fun changePhoneNumber(phoneNumber: String) {
        _state.update { state ->
            state.copy(
                phoneNumber = phoneNumber
            )
        }
    }

    private fun changePostalCode(code: String) {
        _state.update { state ->
            state.copy(
                postalCode = code
            )
        }
    }

    private fun changeLastName(lastName: String) {
        _state.update { state ->
            state.copy(
                lastName = lastName
            )
        }
    }

    private fun changeFirstName(firstName: String) {
        _state.update { state ->
            state.copy(
                firstName = firstName
            )
        }
    }

    private fun changeCity(city: String) {
        _state.update { state ->
            state.copy(
                city = city
            )
        }
    }

    private fun changeAddress(address: String) {
        _state.update { state ->
            state.copy(
                address = address
            )
        }
    }
}