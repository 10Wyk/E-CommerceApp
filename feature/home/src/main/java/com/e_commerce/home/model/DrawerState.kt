package com.e_commerce.home.model

enum class DrawerState {
    Closed,
    Opened;

    fun isOpened(): Boolean = this == Opened
    fun opposite(): DrawerState = when (this) {
        Closed -> Opened
        Opened -> Closed
    }
}