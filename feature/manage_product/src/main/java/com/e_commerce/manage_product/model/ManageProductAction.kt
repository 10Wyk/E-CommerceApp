package com.e_commerce.manage_product.model

import com.e_commerce.shared.domain.model.ProductCategory

sealed interface ManageProductAction {
    data object OnSelectImageCLick : ManageProductAction
    data class OnChangeTitle(val title: String) : ManageProductAction
    data class OnChangeDescription(val description: String) : ManageProductAction
    data class OnSelectProductCategory(val productCategory: ProductCategory) : ManageProductAction
    data class OnChangeWeight(val weight: String) : ManageProductAction
    data class OnChangeFlavors(val flavors: String) : ManageProductAction
    data class OnChangePrice(val price: String) : ManageProductAction
    data object ToggleNew : ManageProductAction
    data object TogglePopular : ManageProductAction
    data object ToggleDiscounted : ManageProductAction
    data object OnUpsertButtonClick : ManageProductAction
    data object OnNavigateBackClick : ManageProductAction
}