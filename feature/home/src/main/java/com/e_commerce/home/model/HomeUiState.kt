package com.e_commerce.home.model

data class HomeUiState(
    val drawerState: DrawerState = DrawerState.Closed,
    val loadingState: Boolean = false
)