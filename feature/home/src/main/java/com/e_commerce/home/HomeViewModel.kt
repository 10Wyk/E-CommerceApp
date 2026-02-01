package com.e_commerce.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.e_commerce.home.model.DrawerState
import com.e_commerce.home.model.HomeAction
import com.e_commerce.home.model.HomeEvent
import com.e_commerce.home.model.HomeUiState
import com.e_commerce.shared.di.DiHelper
import com.e_commerce.shared.domain.repository.CustomerRepository
import com.e_commerce.shared.domain.resourceManager.ResourceManager
import com.e_commerce.shared.presentation.navigation.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {
    private val _eventChannel = Channel<HomeEvent>()
    val eventFlow = _eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()

    private val diComponent = object {
        val customerRepository = DiHelper.get<CustomerRepository>()
        val resourceManager = DiHelper.get<ResourceManager>()
    }

    fun actionHandler(action: HomeAction) {
        when (action) {
            HomeAction.OnSignOutClick -> signOutClick()
            HomeAction.OnToggleDrawer -> toggleDrawer()
            HomeAction.OnProfileClick -> profileClick()
            HomeAction.OnAdminClick -> adminClick()
        }
    }

    private fun adminClick() {
        _eventChannel.trySend(HomeEvent.NavigateToScreen(Screen.Admin))
    }

    private fun profileClick() {
        _eventChannel.trySend(HomeEvent.NavigateToScreen(Screen.Profile))
    }

    private fun toggleDrawer() {
        _state.update { state ->
            state.copy(drawerState = state.drawerState.opposite())
        }
    }

    private fun signOutClick() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _state.update { state ->
                    state.copy(loadingState = true)
                }

                val response = diComponent.customerRepository.signOut()

                _state.update { state ->
                    state.copy(
                        drawerState = DrawerState.Closed,
                        loadingState = false
                    )
                }

                if (response.isSuccess()) {
                    _eventChannel.trySend(HomeEvent.UpdateSuccessMessage("Success sign out"))
                    delay(500)
                    _eventChannel.trySend(HomeEvent.NavigateToScreen(Screen.Auth))
                }
                else if (response.isError())
                    _eventChannel.trySend(HomeEvent.UpdateErrorMessage(response.getErrorMessage()))

            }
        }
    }
}