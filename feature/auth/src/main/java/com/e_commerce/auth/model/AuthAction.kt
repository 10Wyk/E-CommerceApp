package com.e_commerce.auth.model

import dev.gitlive.firebase.auth.FirebaseUser

sealed interface AuthAction {
    data class OnSignUpResult(val result: Result<FirebaseUser?>) : AuthAction
    data object OnSingUpClick : AuthAction
}