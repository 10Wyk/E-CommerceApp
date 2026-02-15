package com.e_commerce.shared.domain.repository

import com.e_commerce.shared.domain.model.Product

interface ProductRepository {
    fun currentUserId(): String?
    suspend fun createProduct(
        product: Product,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )
}