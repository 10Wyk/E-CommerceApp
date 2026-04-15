package com.e_commerce.manage_product

import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.e_commerce.manage_product.model.ManageProductAction
import com.e_commerce.manage_product.model.ManageProductEvent
import com.e_commerce.manage_product.model.ManageProductUiState
import com.e_commerce.shared.di.DiHelper
import com.e_commerce.shared.domain.model.Product
import com.e_commerce.shared.domain.model.ProductCategory
import com.e_commerce.shared.domain.repository.AdminRepository
import com.e_commerce.shared.presentation.utils.ImageState
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
        val adminRepository = DiHelper.get<AdminRepository>()
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
            is ManageProductAction.OnDeleteImage -> deleteImage(action.url)
        }
    }

    private fun navigateBackClick() {
        _eventChannel.trySend(ManageProductEvent.NavigateBack)
    }

    private fun selectImage(image: String?) {
        viewModelScope.launch {
            if (image == null) {
                _eventChannel.trySend(ManageProductEvent.UpdateErrorMessage("No image picked"))
                return@launch
            }

            _state.update { state ->
                state.copy(
                    imageState = ImageState.Loading
                )
            }

            diComponent.adminRepository.addImage(
                file = File(image.toUri()),
                onSuccess = { url ->
                    _state.update { state ->
                        state.copy(
                            imageState = ImageState.Success(url)
                        )
                    }
                },
                onError = { message ->
                    _eventChannel.trySend(ManageProductEvent.UpdateErrorMessage(message))
                    _state.update { state ->
                        state.copy(
                            imageState = ImageState.Error
                        )
                    }
                }
            )
        }
    }

    private fun deleteImage(url: String) {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    imageState = ImageState.Loading
                )
            }

            diComponent.adminRepository.deleteImage(
                url = url,
                onSuccess = {
                    _state.update { state ->
                        state.copy(
                            imageState = ImageState.Idle
                        )
                    }
                },
                onError = { message ->
                    _eventChannel.trySend(ManageProductEvent.UpdateErrorMessage(message))
                    _state.update { state ->
                        state.copy(
                            imageState = ImageState.Error
                        )
                    }
                }
            )
        }
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

            val imageUrl: String? =
                if (state.imageState is ImageState.Success) state.imageState.imageUrl else null

            val product = Product(
                id = id ?: UUID.randomUUID().toString(),
                title = state.title,
                description = state.description,
                flavors = state.flavors?.split(',')?.map { it.trim() },
                thumbnail = imageUrl.orEmpty(),
                category = state.productCategory.title,
                price = state.price.toDoubleOrNull() ?: 0.0,
                weight = state.weight?.toIntOrNull(),
                isNew = state.new,
                isPopular = state.popular,
                isDiscounted = state.discounted
            )

            if (id == null)
                diComponent.adminRepository.createProduct(
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