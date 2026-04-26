package com.e_commerce.shared.domain.repository

import com.e_commerce.shared.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getDiscountedProducts(size: Int): Flow<List<Product>>
    fun getNewProducts(): Flow<List<Product>>
}