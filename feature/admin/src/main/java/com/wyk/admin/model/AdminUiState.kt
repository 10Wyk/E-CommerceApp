package com.wyk.admin.model

import com.e_commerce.shared.domain.model.Product
import com.e_commerce.shared.presentation.utils.RequestState

data class AdminUiState(
    val requestState: RequestState<List<Product>> = RequestState.Idle,
    val query: String = ""
)