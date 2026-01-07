package com.e_commerce.auth

sealed interface AuthAction {
    data object OnSignUpClick : AuthAction
}