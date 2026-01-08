package com.e_commerce.shared.domain.repository

import com.e_commerce.shared.domain.model.Customer

interface CustomerRepository {
    fun createCustomer(
        user: Customer?,
        onSuccess: () -> Unit,
        onError: (error: String) -> Unit
    )
}