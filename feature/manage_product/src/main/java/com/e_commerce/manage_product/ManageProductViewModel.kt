package com.e_commerce.manage_product

import androidx.lifecycle.ViewModel
import com.e_commerce.manage_product.model.ManageProductAction
import com.e_commerce.manage_product.model.ManageProductEvent
import com.e_commerce.manage_product.model.ManageProductUiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

class ManageProductViewModel(
    private val id: String?
) : ViewModel() {
    private val _state = MutableStateFlow(ManageProductUiState())
    val state = _state.asStateFlow()

    private val _eventChannel = Channel<ManageProductEvent>()
    val eventFlow = _eventChannel.receiveAsFlow()

    fun actionHandler(action: ManageProductAction) {
        when (action) {
            is ManageProductAction.OnChangeDescription -> onChangeDescription(action.description)
            is ManageProductAction.OnChangeFlavors -> onChangeFlavors(action.flavors)
            is ManageProductAction.OnChangePrice -> onChangePrice(action.price)
            is ManageProductAction.OnChangeTitle -> onChangeTitle(action.title)
            is ManageProductAction.OnChangeWeight -> onChangeWeight(action.weight)
            ManageProductAction.OnSelectImageCLick -> {}
            is ManageProductAction.OnSelectProductCategory -> {}
            ManageProductAction.OnUpsertButtonClick -> {}
            ManageProductAction.ToggleDiscounted -> toggleDiscounted()
            ManageProductAction.ToggleNew -> toggleNew()
            ManageProductAction.TogglePopular -> togglePopular()
            ManageProductAction.OnNavigateBackClick -> {}
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
}