package com.e_commerce.shared.domain.repository

import com.e_commerce.shared.domain.model.Customer

interface CustomerRepository {
    fun currentUserId(): String?

    suspend fun createCustomer(
        customer: Customer?,
        onSuccess: () -> Unit,
        onError: (error: String) -> Unit
    )

    suspend fun signOut()
}