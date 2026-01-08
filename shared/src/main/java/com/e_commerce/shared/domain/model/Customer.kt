package com.e_commerce.shared.domain.model

import dev.gitlive.firebase.auth.FirebaseUser
import kotlinx.serialization.Serializable

@Serializable
data class Customer(
    val id: String,
    val firstName: String?,
    val lastName: String?,
    val email: String?,
    val country: String? = null,
    val city: String? = null,
    val postalCode: Int? = null,
    val address: String? = null,
    val phoneNumber: PhoneNumber? = null,
    val imageUrl: String? = null,
    val cartItems: List<CartItem> = emptyList()
)

fun FirebaseUser.toCustomer() = Customer(
    id = uid,
    firstName = displayName?.split(" ")?.firstOrNull() ?: "Unknown",
    lastName = displayName?.split(" ")?.lastOrNull() ?: "Unknown",
    email = email,
    imageUrl = photoURL
)

@Serializable
data class PhoneNumber(
    val dialCode: Int,
    val number: String
)