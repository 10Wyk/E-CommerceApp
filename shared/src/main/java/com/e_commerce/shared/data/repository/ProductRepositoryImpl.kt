package com.e_commerce.shared.data.repository

import com.e_commerce.shared.domain.model.Product
import com.e_commerce.shared.domain.repository.ProductRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.Direction
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest

class ProductRepositoryImpl : ProductRepository {
    private companion object {
        const val PRODUCT_COLLECTION = "product"
    }

    //@formatter:off
    private val productCollection = Firebase.firestore.collection(collectionPath = PRODUCT_COLLECTION)
    //@formatter:on

    override fun getDiscountedProducts(size: Int): Flow<List<Product>> = channelFlow {
        productCollection
            .orderBy(field = "createdAt", direction = Direction.ASCENDING)
            .limit(size)
            .where { "isDiscounted" equalTo true }
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

    override fun getNewProducts(): Flow<List<Product>> = channelFlow {
        productCollection
            .orderBy(field = "createdAt", direction = Direction.ASCENDING)
            .where { "isNew" equalTo true }
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
}