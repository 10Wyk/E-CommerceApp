package com.e_commerce.shared.data.repository

import com.e_commerce.shared.domain.model.Customer
import com.e_commerce.shared.domain.repository.CustomerRepository

class CustomerRepositoryImpl() : CustomerRepository {
    override fun createCustomer(
        user: Customer?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

    }
}