package com.e_commerce.shared.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data object Auth : Screen

    @Serializable
    data object HomeGraph : Screen

    @Serializable
    data object ProductOverview : Screen

    @Serializable
    data object Cart : Screen

    @Serializable
    data object Categories : Screen

    @Serializable
    data object Profile : Screen
}