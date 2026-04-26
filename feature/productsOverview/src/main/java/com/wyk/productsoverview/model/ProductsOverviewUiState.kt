package com.wyk.productsoverview.model

import com.e_commerce.shared.domain.model.Product

data class ProductsOverviewUiState(
    val newProducts: List<Product> = emptyList(),
    val discountedProducts: List<Product> = emptyList()
)