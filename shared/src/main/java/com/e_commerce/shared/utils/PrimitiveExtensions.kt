package com.e_commerce.shared.utils

inline fun String.ifNotBlank(value: (String) -> Unit) {
    if (isNotBlank()) value.invoke(this)
}

fun Boolean.asInt() = if (this) 1 else 0

fun Int.asBoolean() = this == 1

fun String.asInt(): Int? {
    return try {
        this.toInt()
    } catch (throwable: Throwable) {
        null
    }
}