package com.wyk.admin.model

sealed interface AdminAction {
    data object OnNavigateBackClick : AdminAction
    data class OnNavigateToManageProductClick(val id: String?) : AdminAction
}