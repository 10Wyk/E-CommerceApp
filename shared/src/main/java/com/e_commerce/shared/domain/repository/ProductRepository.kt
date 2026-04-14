package com.e_commerce.shared.domain.repository

import com.e_commerce.shared.domain.model.Product
import dev.gitlive.firebase.storage.File

interface ProductRepository {
    fun currentUserId(): String?
    suspend fun createProduct(
        product: Product,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )

    suspend fun addImage(
        file: File,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    )
}