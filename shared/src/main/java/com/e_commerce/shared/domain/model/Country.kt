package com.e_commerce.shared.domain.model

import com.e_commerce.shared.presentation.Resources

enum class Country(
    val dialCode: Int,
    val code: String,
    val flag: Int
) {
    Serbia(
        dialCode = 381,
        code = "RS",
        flag = Resources.Flag.Serbia
    ),
    India(
        dialCode = 91,
        code = "IN",
        flag = Resources.Flag.India
    ),
    Usa(
        dialCode = 1,
        code = "US",
        flag = Resources.Flag.Usa
    );

    companion object {
        fun findByDialCode(code: Int?): Country {
            code ?: return Serbia
            return entries.find { country -> country.dialCode == code } ?: Serbia
        }
    }
}