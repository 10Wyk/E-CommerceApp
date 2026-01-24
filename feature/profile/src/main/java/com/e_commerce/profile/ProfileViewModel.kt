package com.e_commerce.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.e_commerce.profile.model.ProfileAction
import com.e_commerce.profile.model.ProfileEvent
import com.e_commerce.profile.model.ProfileUiState
import com.e_commerce.shared.di.DiHelper
import com.e_commerce.shared.domain.model.Country
import com.e_commerce.shared.domain.repository.CustomerRepository
import com.e_commerce.shared.utils.RequestState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(ProfileUiState())
    val state = _state.asStateFlow()

    private val _eventChannel = Channel<ProfileEvent>()
    val eventFlow = _eventChannel.receiveAsFlow()

    private val diComponent = object {
        val customerRepository = DiHelper.get<CustomerRepository>()
    }

    init {
        fetchCustomer()
    }

    fun actonHandler(action: ProfileAction) {
        when (action) {
            is ProfileAction.OnChangeAddress -> changeAddress(action.address)
            is ProfileAction.OnChangeCity -> changeCity(action.city)
            is ProfileAction.OnChangeFirstName -> changeFirstName(action.firstName)
            is ProfileAction.OnChangeLastName -> changeLastName(action.lastName)
            is ProfileAction.OnChangePostalCode -> changePostalCode(action.code)
            is ProfileAction.OnChangePhoneNumber -> changePhoneNumber(action.phoneNumber)
            is ProfileAction.OnPickCountry -> onPickCountry(action.country)
            ProfileAction.OnNavigateBackClick -> navigateBackClick()
        }
    }

    private fun navigateBackClick() {
        _eventChannel.trySend(ProfileEvent.NavigateBack)
    }

    private fun onPickCountry(country: Country) {
        _state.update { state ->
            state.copy(
                country = country
            )
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
            val postalCode = code.toIntOrNull() ?: return
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

    private fun fetchCustomer() {
        viewModelScope.launch {
            diComponent.customerRepository
                .readCustomerFlow()
                .onStart {
                    _state.update { state ->
                        state.copy(
                            requestState = RequestState.Loading
                        )
                    }
                }
                .catch { throwable ->
                    _state.update { state ->
                        state.copy(
                            requestState = RequestState.Error(throwable.message.orEmpty())
                        )
                    }
                }
                .collectLatest { customerData ->
                    if (customerData.isSuccess()) {
                        _state.update { state ->
                            state.copy(
                                requestState = RequestState.Success(Unit),
                                firstName = customerData.getSuccessData().firstName.orEmpty(),
                                lastName = customerData.getSuccessData().lastName.orEmpty(),
                                email = customerData.getSuccessData().email.orEmpty(),
                                city = customerData.getSuccessData().city,
                                postalCode = customerData.getSuccessData().postalCode,
                                address = customerData.getSuccessData().address,
                                phoneNumber = customerData.getSuccessData().phoneNumber?.number,
                                country = Country.findByDialCode(customerData.getSuccessData().phoneNumber?.dialCode)
                            )
                        }
                    } else if (customerData.isError()) {
                        _state.update { state ->
                            state.copy(
                                requestState = RequestState.Error(customerData.getErrorMessage())
                            )
                        }
                    }
                }
        }
    }
}