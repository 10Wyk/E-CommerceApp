package com.e_commerce.manage_product.model

sealed interface ManageProductEvent {
    data class UpdateErrorMessage(val message: String) : ManageProductEvent
    data class UpdateSuccessMessage(val message: String) : ManageProductEvent
    data object NavigateBack : ManageProductEvent
}