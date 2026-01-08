package com.e_commerce.shared.data.resourceManager

import android.content.Context
import androidx.annotation.StringRes
import com.e_commerce.shared.domain.resourceManager.ResourceManager

class ResourceManagerImpl(
    private val context: Context
) : ResourceManager {
    override fun readString(@StringRes id: Int, vararg arguments: Any): String {
        return context.getString(id, *arguments)
    }

    override fun readString(@StringRes id: Int): String {
        return context.getString(id)
    }
}