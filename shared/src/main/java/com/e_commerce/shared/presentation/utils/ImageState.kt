package com.e_commerce.shared.presentation.utils

sealed interface ImageState {
    data object Idle : ImageState
    data object Loading : ImageState
    data object Error : ImageState
    data class Success(val imageUrl: String) : ImageState
}