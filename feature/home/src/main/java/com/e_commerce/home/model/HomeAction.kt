package com.e_commerce.home.model

sealed interface HomeAction {
    data object OnSignOutClick : HomeAction
    data object OnProfileClick : HomeAction
    data object OnAdminClick : HomeAction
    data object OnToggleDrawer : HomeAction
}