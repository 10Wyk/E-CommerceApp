package com.wyk.admin.model

import com.e_commerce.shared.domain.model.Product

data class AdminUiState(
    val product: List<Product> = emptyList()
)