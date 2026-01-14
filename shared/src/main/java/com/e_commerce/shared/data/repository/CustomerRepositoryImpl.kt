package com.e_commerce.shared.data.repository

import com.e_commerce.shared.R
import com.e_commerce.shared.domain.model.Customer
import com.e_commerce.shared.domain.repository.CustomerRepository
import com.e_commerce.shared.domain.resourceManager.ResourceManager
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore

class CustomerRepositoryImpl(
    private val resourceManager: ResourceManager
) : CustomerRepository {
    private val customerCollection = Firebase.firestore.collection(collectionPath = "customer")

    override fun currentUserId(): String? {
        return Firebase.auth.currentUser?.uid
    }

    override suspend fun createCustomer(
        customer: Customer?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            if (customer != null) {
                val customerExists = customerCollection.document(customer.id).get().exists
                if (customerExists) {
                    onSuccess()
                } else {
                    customerCollection.document(customer.id).set(customer)
                    onSuccess()
                }
            } else onError(resourceManager.readString(R.string.msg_user_not_available))
        } catch (throwable: Throwable) {
            onError(
                resourceManager.readString(
                    R.string.msg_error_creating_a_customer,
                    throwable.message.orEmpty()
                )
            )
        }
    }

    override suspend fun signOut() {
        Firebase.auth.signOut()
    }
}