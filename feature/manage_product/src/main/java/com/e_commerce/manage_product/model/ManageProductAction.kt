package com.e_commerce.manage_product.model

import com.e_commerce.shared.domain.model.ProductCategory
import dev.gitlive.firebase.storage.File

sealed interface ManageProductAction {
    data class OnChangeTitle(val title: String) : ManageProductAction
    data class OnChangeDescription(val description: String) : ManageProductAction
    data class OnSelectProductCategory(val productCategory: ProductCategory) : ManageProductAction
    data class OnChangeWeight(val weight: String) : ManageProductAction
    data class OnChangeFlavors(val flavors: String) : ManageProductAction
    data class OnChangePrice(val price: String) : ManageProductAction
    data class OnSelectImage(val image: File?) : ManageProductAction
    data object ToggleNew : ManageProductAction
    data object TogglePopular : ManageProductAction
    data object ToggleDiscounted : ManageProductAction
    data object OnUpsertButtonClick : ManageProductAction
    data object OnNavigateBackClick : ManageProductAction
}