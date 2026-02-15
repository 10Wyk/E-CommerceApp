package com.wyk.admin.model

sealed interface AdminEvent {
    data object NavigateBack : AdminEvent
    data class NavigateToManageProduct(val id: String?) : AdminEvent
}