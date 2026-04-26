package com.wyk.productsoverview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.e_commerce.shared.di.DiHelper
import com.e_commerce.shared.domain.repository.ProductRepository
import com.e_commerce.shared.domain.resourceManager.ResourceManager
import com.wyk.productsoverview.model.ProductsOverviewUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class ProductsOverviewViewModel : ViewModel() {
    private val _state = MutableStateFlow(ProductsOverviewUiState())
    val state = _state.asStateFlow()

    private val diComponent = object {
        val productRepository = DiHelper.get<ProductRepository>()
        val resourceManager = DiHelper.get<ResourceManager>()
    }

    init {
        viewModelScope.launch {
            supervisorScope {
                val newJob = launch {
                    fetchNewProducts()
                }
                val discountedJob = launch {
                    fetchDiscountedProducts()
                }

                newJob.join()
                discountedJob.join()
            }
        }
    }

    private fun fetchNewProducts() {
        viewModelScope.launch {
            diComponent.productRepository.getNewProducts()
                .catch { throwable ->

                }.collect { products ->
                    _state.update { state ->
                        state.copy(
                            newProducts = products
                        )
                    }
                }
        }
    }

    private fun fetchDiscountedProducts() {
        viewModelScope.launch {
            diComponent.productRepository.getDiscountedProducts(3)
                .catch { throwable ->

                }.collect { products ->
                    _state.update { state ->
                        state.copy(
                            discountedProducts = products
                        )
                    }
                }
        }
    }
}