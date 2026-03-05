package com.e_commerce.manage_product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.e_commerce.manage_product.model.ManageProductAction
import com.e_commerce.manage_product.model.ManageProductEvent
import com.e_commerce.manage_product.model.ManageProductUiState
import com.e_commerce.shared.di.DiHelper
import com.e_commerce.shared.domain.model.Product
import com.e_commerce.shared.domain.model.ProductCategory
import com.e_commerce.shared.domain.repository.ProductRepository
import com.e_commerce.shared.presentation.utils.RequestState
import dev.gitlive.firebase.storage.File
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import java.util.UUID

class ManageProductViewModel(
    private val id: String?
) : ViewModel() {
    private val _state = MutableStateFlow(ManageProductUiState())
    val state = _state.asStateFlow()

    private val _eventChannel = Channel<ManageProductEvent>()
    val eventFlow = _eventChannel.receiveAsFlow()

    private val diComponent = object {
        val productRepository = DiHelper.get<ProductRepository>()
    }

    fun actionHandler(action: ManageProductAction) {
        when (action) {
            is ManageProductAction.OnChangeDescription -> onChangeDescription(action.description)
            is ManageProductAction.OnChangeFlavors -> onChangeFlavors(action.flavors)
            is ManageProductAction.OnChangePrice -> onChangePrice(action.price)
            is ManageProductAction.OnChangeTitle -> onChangeTitle(action.title)
            is ManageProductAction.OnChangeWeight -> onChangeWeight(action.weight)
            is ManageProductAction.OnSelectProductCategory -> onSelectProductCategory(action.productCategory)
            ManageProductAction.OnUpsertButtonClick -> upsertProduct()
            ManageProductAction.ToggleDiscounted -> toggleDiscounted()
            ManageProductAction.ToggleNew -> toggleNew()
            ManageProductAction.TogglePopular -> togglePopular()
            ManageProductAction.OnNavigateBackClick -> navigateBackClick()
            is ManageProductAction.OnSelectImage -> selectImage(action.image)
        }
    }

    private fun navigateBackClick() {
        _eventChannel.trySend(ManageProductEvent.NavigateBack)
    }

    private fun selectImage(image: File?) {

    }

    private fun onSelectProductCategory(productCategory: ProductCategory) {
        _state.update { state ->
            state.copy(
                productCategory = productCategory
            )
        }
    }

    private fun onChangeWeight(weight: String) {
        _state.update { state ->
            state.copy(
                weight = weight
            )
        }
    }

    private fun onChangeTitle(title: String) {
        _state.update { state ->
            state.copy(
                title = title
            )
        }
    }

    private fun onChangePrice(price: String) {
        _state.update { state ->
            state.copy(
                price = price
            )
        }
    }

    private fun onChangeFlavors(flavors: String) {
        _state.update { state ->
            state.copy(
                flavors = flavors
            )
        }
    }

    private fun onChangeDescription(description: String) {
        _state.update { state ->
            state.copy(
                description = description
            )
        }
    }

    private fun togglePopular() {
        _state.update { state ->
            state.copy(
                popular = !state.popular
            )
        }
    }

    private fun toggleNew() {
        _state.update { state ->
            state.copy(
                new = !state.new
            )
        }
    }

    private fun toggleDiscounted() {
        _state.update { state ->
            state.copy(
                discounted = !state.discounted
            )
        }
    }

    fun upsertProduct() {
        viewModelScope.launch {
            val state = _state.updateAndGet { state ->
                state.copy(
                    requestState = RequestState.Loading
                )
            }

            val product = Product(
                id = id ?: UUID.randomUUID().toString(),
                title = state.title,
                description = state.description,
                flavors = state.flavors?.split(',')?.map { it.trim() },
                thumbnail = state.imageUrl.orEmpty(),
                category = state.productCategory.title,
                price = state.price.toDoubleOrNull() ?: 0.0,
                weight = state.weight?.toIntOrNull(),
                isNew = state.new,
                isPopular = state.popular,
                isDiscounted = state.discounted
            )

            if (id == null)
                diComponent.productRepository.createProduct(
                    product = product,
                    onSuccess = {
                        _state.update { state ->
                            state.copy(
                                requestState = RequestState.Success(Unit)
                            )
                        }
                        _eventChannel.trySend(ManageProductEvent.UpdateSuccessMessage("Product added"))
                    },
                    onError = {
                        _state.update { state ->
                            state.copy(
                                requestState = RequestState.Error(it)
                            )
                        }
                        _eventChannel.trySend(ManageProductEvent.UpdateSuccessMessage(it))
                    }
                )

        }
    }
}