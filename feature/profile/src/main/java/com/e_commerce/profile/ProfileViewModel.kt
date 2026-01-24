package com.e_commerce.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.e_commerce.profile.model.ProfileAction
import com.e_commerce.profile.model.ProfileEvent
import com.e_commerce.profile.model.ProfileUiState
import com.e_commerce.shared.utils.asInt
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(ProfileUiState())
    val state = _state.asStateFlow()

    private val _eventChannel = Channel<ProfileEvent>()
    val eventFlow = _eventChannel.receiveAsFlow()

    fun actonHandler(action: ProfileAction) {
        when (action) {
            is ProfileAction.OnChangeAddress -> changeAddress(action.address)
            is ProfileAction.OnChangeCity -> changeCity(action.city)
            is ProfileAction.OnChangeFirstName -> changeFirstName(action.firstName)
            is ProfileAction.OnChangeLastName -> changeLastName(action.lastName)
            is ProfileAction.OnChangePostalCode -> changePostalCode(action.code)
            is ProfileAction.OnChangePhoneNumber -> changePhoneNumber(action.phoneNumber)
            ProfileAction.OnNavigateBackClick -> navigateBackClick()
        }
    }

    private fun navigateBackClick() {
        _eventChannel.trySend(ProfileEvent.NavigateBack)
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
            val postalCode = code.asInt() ?: return
            state.copy(
                postalCode = postalCode
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