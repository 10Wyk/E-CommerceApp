package com.wyk.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.e_commerce.shared.di.DiHelper
import com.e_commerce.shared.domain.repository.AdminRepository
import com.e_commerce.shared.domain.resourceManager.ResourceManager
import com.e_commerce.shared.presentation.utils.RequestState
import com.wyk.admin.model.AdminAction
import com.wyk.admin.model.AdminEvent
import com.wyk.admin.model.AdminUiState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AdminViewModel : ViewModel() {
    private val _state = MutableStateFlow(AdminUiState())
    val state = _state.asStateFlow()

    private val _channelEvent = Channel<AdminEvent>()
    val eventFlow = _channelEvent.receiveAsFlow()

    private val diComponent = object {
        val adminRepository = DiHelper.get<AdminRepository>()
        val resourceManager = DiHelper.get<ResourceManager>()
    }
    private var searchJob: Job? = null

    init {
        fetchData()
    }

    fun actionHandler(action: AdminAction) {
        when (action) {
            AdminAction.OnNavigateBackClick -> navigateBackClick()
            is AdminAction.OnNavigateToManageProductClick -> onNavigateToManageProductClick(action.id)
            is AdminAction.OnQueryChange -> queryChange(action.query)
        }
    }

    @OptIn(FlowPreview::class)
    private fun queryChange(query: String) {
        searchJob?.cancel()
        _state.update { state ->
            state.copy(
                query = query
            )
        }

        searchJob = viewModelScope.launch {
            diComponent.adminRepository.searchProduct(query)
                .debounce(300)
                .onStart {
                    _state.update { state ->
                        state.copy(
                            requestState = RequestState.Loading,
                        )
                    }
                }
                .catch { throwable ->
                    if (throwable !is CancellationException)
                        _state.update { state ->
                            state.copy(
                                requestState = RequestState.Error(message = "Error while loading products: ${throwable.message}")
                            )
                        }
                }
                .collect { products ->
                    _state.update { state ->
                        state.copy(
                            requestState = RequestState.Success(products)
                        )
                    }
                }
        }
    }

    private fun navigateBackClick() {
        _channelEvent.trySend(AdminEvent.NavigateBack)
    }

    private fun onNavigateToManageProductClick(id: String?) {
        _channelEvent.trySend(AdminEvent.NavigateToManageProduct(id))
    }

    private fun fetchData() {
        viewModelScope.launch {
            diComponent.adminRepository.readLastProducts()
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
                            requestState = RequestState.Error(message = "Error while loading products: ${throwable.message}")
                        )
                    }

                    _channelEvent.trySend(AdminEvent.UpdateErrorMessage("Error while loading products: ${throwable.message}"))
                }
                .collect { products ->
                    _state.update { state ->
                        state.copy(
                            requestState = RequestState.Success(products)
                        )
                    }
                }
        }
    }
}