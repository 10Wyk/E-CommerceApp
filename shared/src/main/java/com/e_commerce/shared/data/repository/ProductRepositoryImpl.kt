package com.e_commerce.shared.data.repository

import com.e_commerce.shared.R
import com.e_commerce.shared.domain.model.Product
import com.e_commerce.shared.domain.repository.ProductRepository
import com.e_commerce.shared.domain.resourceManager.ResourceManager
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.storage.File
import dev.gitlive.firebase.storage.storage
import kotlinx.coroutines.withTimeout
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ProductRepositoryImpl(
    private val resourceManager: ResourceManager
) : ProductRepository {
    private companion object {
        const val PRODUCT_COLLECTION = "product"
    }

    //@formatter:off
    private val customerCollection = Firebase.firestore.collection(collectionPath = PRODUCT_COLLECTION)
    private val productImagesCollection = Firebase.storage
    //@formatter:on

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
                onSuccess()
            } else onError(resourceManager.readString(R.string.msg_user_not_available))
        } catch (e: Exception) {
            onError(e.message.orEmpty())
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun addImage(
        file: File,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        if (currentUserId() != null) {
            //@formatter:off
            val imagePath = productImagesCollection.reference.child(
                path = "images/${Uuid.random().toHexString()}"
            )
            //@formatter:on
            try {
                withTimeout(timeMillis = 20_000L) {
                    imagePath.putFile(file)
                    onSuccess(imagePath.getDownloadUrl())
                }
            } catch (throwable: Throwable) {
                onError(throwable.message ?: resourceManager.readString(R.string.lbl_unknown))
            }
        }
    }
}