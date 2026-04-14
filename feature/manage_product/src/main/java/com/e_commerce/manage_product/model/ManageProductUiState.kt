package com.e_commerce.manage_product.model

import com.e_commerce.shared.domain.model.ProductCategory
import com.e_commerce.shared.presentation.utils.ImageState
import com.e_commerce.shared.presentation.utils.RequestState

data class ManageProductUiState(
    val imageState: ImageState = ImageState.Idle,
    val title: String = "",
    val description: String = "",
    val productCategory: ProductCategory = ProductCategory.Protein,
    val weight: String? = null,
    val flavors: String? = null,
    val price: String = "",
    val new: Boolean = false,
    val popular: Boolean = false,
    val discounted: Boolean = false,
    val requestState: RequestState<Unit> = RequestState.Success(Unit)
)