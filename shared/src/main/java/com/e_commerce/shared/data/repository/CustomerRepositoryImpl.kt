package com.e_commerce.shared.data.repository

import com.e_commerce.shared.R
import com.e_commerce.shared.domain.model.Customer
import com.e_commerce.shared.domain.model.toCustomer
import com.e_commerce.shared.domain.repository.CustomerRepository
import com.e_commerce.shared.domain.resourceManager.ResourceManager
import com.e_commerce.shared.utils.RequestState
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest

class CustomerRepositoryImpl(
    private val resourceManager: ResourceManager
) : CustomerRepository {
    private companion object {
        const val CUSTOMER_COLLECTION = "customer"
    }

    private val customerCollection =
        Firebase.firestore.collection(collectionPath = CUSTOMER_COLLECTION)

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
        } catch (exception: CancellationException) {
            throw exception
        } catch (exception: Exception) {

            onError(
                resourceManager.readString(
                    R.string.msg_error_creating_a_customer,
                    exception.message.orEmpty()
                )
            )
        }
    }

    override fun readCustomerFlow(): Flow<RequestState<Customer>> = channelFlow {
        try {
            val userId = currentUserId()
            if (userId == null) send(RequestState.Error(resourceManager.readString(R.string.msg_user_not_available)))
            else {
                val database = Firebase.firestore
                database.collection(collectionPath = CUSTOMER_COLLECTION)
                    .document(userId)
                    .snapshots
                    .collectLatest { document ->
                        send(RequestState.Success(document.toCustomer()))
                    }
            }
        } catch (exception: CancellationException) {
            throw exception
        } catch (exception: Exception) {
            send(RequestState.Error("Error while reading a Customer information: ${exception.message}"))
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