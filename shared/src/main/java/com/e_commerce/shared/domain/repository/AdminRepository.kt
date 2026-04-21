package com.e_commerce.shared.domain.repository

import com.e_commerce.shared.domain.model.Product
import com.e_commerce.shared.presentation.utils.RequestState
import dev.gitlive.firebase.storage.File
import kotlinx.coroutines.flow.Flow

interface AdminRepository {
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

    suspend fun deleteImage(
        url: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )

    fun readLastProducts(limit: Int = 10): Flow<List<Product>>
    suspend fun readProductById(id: String): RequestState<Product>
    suspend fun updateProduct(
        product: Product,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )

    suspend fun updateThumbnail(
        id: String,
        url: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )

    fun searchProduct(query: String): Flow<List<Product>>
    suspend fun deleteProduct(
        uid: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )
}