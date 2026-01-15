package com.e_commerce.shared.utils

inline fun String.ifNotBlank(value: (String) -> Unit) {
    if (isNotBlank()) value.invoke(this)
}