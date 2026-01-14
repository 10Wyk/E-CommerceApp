package com.e_commerce.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.e_commerce.home.model.HomeAction
import com.e_commerce.home.model.HomeEvent
import com.e_commerce.shared.di.DiHelper
import com.e_commerce.shared.domain.repository.CustomerRepository
import com.e_commerce.shared.domain.resourceManager.ResourceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {
    private val _eventChannel = Channel<HomeEvent>()
    val eventState = _eventChannel.receiveAsFlow()

    private val diComponent = object {
        val customerRepository = DiHelper.get<CustomerRepository>()
        val resourceManager = DiHelper.get<ResourceManager>()
    }

    fun actionHandler(action: HomeAction) {
        when (action) {
            HomeAction.OnSignOutClick -> signOutClick()
        }
    }

    private fun signOutClick() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    diComponent.customerRepository.signOut()
                    _eventChannel.trySend(HomeEvent.NavigateToAuth)
                } catch (_: Throwable) {

                }
            }
        }
    }
}