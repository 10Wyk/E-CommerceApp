package com.e_commerce.shared.domain.repository

import com.e_commerce.shared.domain.model.Customer
import com.e_commerce.shared.utils.RequestState
import kotlinx.coroutines.flow.Flow

interface CustomerRepository {
    fun currentUserId(): String?

    suspend fun createCustomer(
        customer: Customer?,
        onSuccess: () -> Unit,
        onError: (error: String) -> Unit
    )

    fun readCustomerFlow(): Flow<RequestState<Customer>>

    suspend fun signOut(): RequestState<Unit>
}