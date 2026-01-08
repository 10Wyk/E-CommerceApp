package com.e_commerce.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.e_commerce.auth.model.AuthAction
import com.e_commerce.auth.model.AuthEvent
import com.e_commerce.shared.di.DiHelper
import com.e_commerce.shared.domain.repository.CustomerRepository
import dev.gitlive.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _eventFlow = MutableSharedFlow<AuthEvent?>(
        replay = 1,
        extraBufferCapacity = 0,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val eventFlow = _eventFlow.asSharedFlow()

    private val diComponent = object {
        val customerRepository = DiHelper.get<CustomerRepository>()
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
                _eventFlow.tryEmit(AuthEvent.UpdateSuccessMessage("Authenticated"))
            }.onFailure { throwable ->
                when (throwable) {
                    is UnresolvedAddressException, is UnknownHostException,
                    is ConnectException, is SSLHandshakeException,
                    is IOException ->
                        _eventFlow.tryEmit(AuthEvent.UpdateErrorMessage("Internet connection unavailable."))

                    is IllegalStateException ->
                        _eventFlow.tryEmit(AuthEvent.UpdateErrorMessage("Sign in canceled."))

                    else -> _eventFlow.tryEmit(
                        AuthEvent.UpdateErrorMessage(
                            throwable.message ?: "Unknown"
                        )
                    )
                }
            }
        }
    }
}