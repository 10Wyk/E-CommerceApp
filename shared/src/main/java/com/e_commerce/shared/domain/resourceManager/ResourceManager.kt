package com.e_commerce.shared.domain.resourceManager

import androidx.annotation.StringRes

interface ResourceManager {
    fun readString(@StringRes id: Int, vararg arguments: Any): String
    fun readString(@StringRes id: Int): String
}