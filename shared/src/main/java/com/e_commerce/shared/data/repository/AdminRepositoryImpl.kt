package com.e_commerce.shared.data.repository

import com.e_commerce.shared.R
import com.e_commerce.shared.domain.model.Product
import com.e_commerce.shared.domain.repository.AdminRepository
import com.e_commerce.shared.domain.resourceManager.ResourceManager
import com.e_commerce.shared.presentation.utils.RequestState
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseException
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.storage.File
import dev.gitlive.firebase.storage.storage
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withTimeout
import okio.IOException
import java.net.ConnectException
import java.net.UnknownHostException
import java.nio.channels.UnresolvedAddressException
import javax.net.ssl.SSLHandshakeException
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class AdminRepositoryImpl(
    private val resourceManager: ResourceManager
) : AdminRepository {
    private companion object {
        const val PRODUCT_COLLECTION = "product"
    }

    //@formatter:off
    private val productCollection = Firebase.firestore.collection(collectionPath = PRODUCT_COLLECTION)
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
                productCollection.document(product.id)
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
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                onError(e.message ?: resourceManager.readString(R.string.lbl_unknown))
            }
        }
    }

    override suspend fun deleteImage(
        url: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val path = extractStoragePath(url)
            if (path != null) {
                productImagesCollection.reference(path).delete()
                onSuccess()
            } else {
                onError("Storage path is null")
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            onError("Error occurred during removing an image: ${e.message}")
        }
    }

    override fun readLastProducts(limit: Int): Flow<List<Product>> = channelFlow {
        val userId = currentUserId()
        if (userId == null) throw Exception(resourceManager.readString(R.string.msg_user_not_available))

        productCollection
            .limit(limit = limit)
            .snapshots
            .collectLatest { querySnapshot ->
                val products = querySnapshot.documents.map { documentSnapshot ->
                    Product(
                        id = documentSnapshot.id,
                        title = documentSnapshot.get(field = "title"),
                        createdAt = documentSnapshot.get(field = "createdAt"),
                        description = documentSnapshot.get(field = "description"),
                        thumbnail = documentSnapshot.get(field = "thumbnail"),
                        category = documentSnapshot.get(field = "category"),
                        flavors = documentSnapshot.get(field = "flavors"),
                        weight = documentSnapshot.get(field = "weight"),
                        price = documentSnapshot.get(field = "price"),
                        isPopular = documentSnapshot.get(field = "isPopular"),
                        isDiscounted = documentSnapshot.get(field = "isDiscounted"),
                        isNew = documentSnapshot.get(field = "isNew"),
                    )
                }

                send(products)
            }
    }

    override suspend fun readProductById(id: String): RequestState<Product> {
        try {
            val userId = currentUserId()
            if (userId == null) return RequestState.Error(resourceManager.readString(R.string.msg_user_not_available))
            else {
                val productDocument = productCollection
                    .document(id)
                    .get()

                if (!productDocument.exists) return RequestState.Error("Product does not found")

                val product = Product(
                    id = productDocument.id,
                    title = productDocument.get(field = "title"),
                    createdAt = productDocument.get(field = "createdAt"),
                    description = productDocument.get(field = "description"),
                    thumbnail = productDocument.get(field = "thumbnail"),
                    category = productDocument.get(field = "category"),
                    flavors = productDocument.get(field = "flavors"),
                    weight = productDocument.get(field = "weight"),
                    price = productDocument.get(field = "price"),
                    isPopular = productDocument.get(field = "isPopular"),
                    isDiscounted = productDocument.get(field = "isDiscounted"),
                    isNew = productDocument.get(field = "isNew"),
                )
                return RequestState.Success(product)
            }
        } catch (exception: CancellationException) {
            throw exception
        } catch (exception: Exception) {
            val message = when (exception) {
                is UnresolvedAddressException, is UnknownHostException,
                is ConnectException, is SSLHandshakeException, is FirebaseException,
                is IOException -> resourceManager.readString(R.string.msg_internet_not_available)

                else -> "Error while reading a Customer information: ${exception.message}"
            }
            return RequestState.Error(message)
        }
    }

    override suspend fun updateProduct(
        product: Product,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val userId = currentUserId()
            if (userId != null) {
                val productDocument = productCollection
                    .document(product.id)
                    .get()
                if (!productDocument.exists)
                    onError("Product does not found")
                else {
                    productCollection
                        .document(product.id)
                        .update(product)
                    onSuccess()
                }
            } else onError(resourceManager.readString(R.string.msg_user_not_available))
        } catch (e: Exception) {
            onError(e.message.orEmpty())
        }
    }

    private fun extractStoragePath(url: String): String? {
        val startIndex = url.indexOf("/o/") + 3
        if (startIndex < 3) return null

        val endIndex = url.indexOf("?", startIndex)
        val encodedPath = if (endIndex != -1) {
            url.substring(startIndex, endIndex)
        } else
            url.substring(startIndex)

        return decodeThePath(encodedPath)
    }

    private fun decodeThePath(encodedPath: String): String {
        return encodedPath
            .replace("%2F", "/")
            .replace("%20", " ")
    }
}