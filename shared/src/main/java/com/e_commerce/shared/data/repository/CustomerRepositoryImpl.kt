package com.e_commerce.shared.data.repository

import com.e_commerce.shared.R
import com.e_commerce.shared.domain.model.Customer
import com.e_commerce.shared.domain.repository.CustomerRepository
import com.e_commerce.shared.domain.resourceManager.ResourceManager
import com.e_commerce.shared.utils.RequestState
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.CancellationException

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

    override suspend fun signOut(): RequestState<Unit> {
        try {
            Firebase.auth.signOut()
            return RequestState.Success(data = Unit)
        } catch (exception: CancellationException) {
            throw exception
        } catch (exception: Exception) {
            return RequestState.Error(message = exception.message ?: "Unexpected Error")
        }
    }
}