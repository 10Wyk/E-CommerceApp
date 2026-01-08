package com.e_commerce.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.e_commerce.auth.model.AuthAction
import com.e_commerce.auth.model.AuthEvent
import com.e_commerce.shared.R
import com.e_commerce.shared.di.DiHelper
import com.e_commerce.shared.domain.model.toCustomer
import com.e_commerce.shared.domain.repository.CustomerRepository
import com.e_commerce.shared.domain.resourceManager.ResourceManager
import dev.gitlive.firebase.FirebaseException
import dev.gitlive.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.IOException
import java.net.ConnectException
import java.net.UnknownHostException
import java.nio.channels.UnresolvedAddressException
import javax.net.ssl.SSLHandshakeException

class AuthViewModel : ViewModel() {
    private val _state = MutableStateFlow(false)
    val state = _state.asStateFlow()

    private val _eventChannel = Channel<AuthEvent?>()
    val eventFlow = _eventChannel.receiveAsFlow()

    private val diComponent = object {
        val customerRepository = DiHelper.get<CustomerRepository>()
        val resourceManager = DiHelper.get<ResourceManager>()
    }

    fun actionHandler(action: AuthAction) {
        when (action) {
            is AuthAction.OnSignUpResult -> signUpResult(action.result)
            AuthAction.OnSingUpClick -> signUpClick()
        }
    }

    private fun signUpClick() {
        _state.update { true }
    }

    private fun signUpResult(result: Result<FirebaseUser?>) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { false }

            result.onSuccess { firebaseUser ->
                diComponent.customerRepository.createCustomer(
                    customer = firebaseUser?.toCustomer(),
                    onError = { error ->
                        _eventChannel.trySend(AuthEvent.UpdateErrorMessage(error))
                    },
                    onSuccess = {
                        _eventChannel.trySend(
                            AuthEvent.UpdateSuccessMessage(
                                diComponent.resourceManager.readString(
                                    R.string.msg_authenticated
                                )
                            )
                        )
                    }
                )
            }.onFailure { throwable ->
                println(throwable)

                when (throwable) {
                    is UnresolvedAddressException, is UnknownHostException,
                    is ConnectException, is SSLHandshakeException, is FirebaseException,
                    is IOException ->
                        _eventChannel.trySend(
                            AuthEvent.UpdateErrorMessage(
                                diComponent.resourceManager.readString(R.string.msg_internet_not_available)
                            )
                        )

                    is IllegalStateException ->
                        _eventChannel.trySend(
                            AuthEvent.UpdateErrorMessage(
                                diComponent.resourceManager.readString(
                                    R.string.msg_sign_in_canceled
                                )
                            )
                        )

                    else -> _eventChannel.trySend(
                        AuthEvent.UpdateErrorMessage(
                            throwable.message
                                ?: diComponent.resourceManager.readString(R.string.lbl_unknown)
                        )
                    )
                }
            }
        }
    }
}