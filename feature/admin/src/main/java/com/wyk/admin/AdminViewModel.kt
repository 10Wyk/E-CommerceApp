package com.wyk.admin

import androidx.lifecycle.ViewModel
import com.wyk.admin.model.AdminAction
import com.wyk.admin.model.AdminEvent
import com.wyk.admin.model.AdminUiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class AdminViewModel : ViewModel() {
    private val _state = MutableStateFlow(AdminUiState())
    val state = _state.asStateFlow()

    private val _channelEvent = Channel<AdminEvent>()
    val eventFlow = _channelEvent.receiveAsFlow()

    fun actionHandler(action: AdminAction) {
        when (action) {
            else -> {}
        }
    }
}