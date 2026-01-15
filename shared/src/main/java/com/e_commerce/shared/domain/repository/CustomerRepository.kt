package com.e_commerce.shared.domain.repository

import com.e_commerce.shared.domain.model.Customer
import com.e_commerce.shared.utils.RequestState

interface CustomerRepository {
    fun currentUserId(): String?

    suspend fun createCustomer(
        customer: Customer?,
        onSuccess: () -> Unit,
        onError: (error: String) -> Unit
    )

    suspend fun signOut(): RequestState<Unit>
}