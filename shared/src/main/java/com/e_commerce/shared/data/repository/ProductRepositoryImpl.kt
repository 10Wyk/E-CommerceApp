package com.e_commerce.shared.data.repository

import com.e_commerce.shared.R
import com.e_commerce.shared.domain.model.Product
import com.e_commerce.shared.domain.repository.ProductRepository
import com.e_commerce.shared.domain.resourceManager.ResourceManager
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore

class ProductRepositoryImpl(
    private val resourceManager: ResourceManager
) : ProductRepository {
    private companion object {
        const val PRODUCT_COLLECTION = "product"
    }

    private val customerCollection =
        Firebase.firestore.collection(collectionPath = PRODUCT_COLLECTION)

    override fun currentUserId(): String? = Firebase.auth.currentUser?.uid

    override suspend fun createProduct(
        product: Product,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val userId = currentUserId()
            if (userId != null) {
                customerCollection.document(product.id)
                    .set(product)

            } else onError(resourceManager.readString(R.string.msg_user_not_available))
        } catch (e: Exception) {
            onError(e.message.orEmpty())
        }
    }
}