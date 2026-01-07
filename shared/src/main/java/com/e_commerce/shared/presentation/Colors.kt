package com.e_commerce.shared.presentation

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

val GrayLighter = Color(0xFFFAFAFA)
val Gray = Color(0xFFF1F1F1)
val GrayDarker = Color(0xFFEBEBEB)

val Yellowish = Color(0xFFEEFF00)
val Orange = Color(0xFFF24C00)
val White = Color(0xFFFFFFFF)
val Black = Color(0xFF000000)
val Red = Color(0xFFDD0000)

val CategoryYellow = Color(0xFFFFC738)
val CategoryBlue = Color(0xFF38B3FF)
val CategoryGreen = Color(0xFF19D109)
val CategoryPurple = Color(0xFF8E5EFF)
val CategoryRed = Color(0xFFFF5E60)

data class AppColor(
    val surface: Color,
    val surfaceLighter: Color,
    val surfaceDarker: Color,
    val surfaceBrand: Color,
    val surfaceError: Color,
    val surfaceSecondary: Color,

    val borderIdle: Color,
    val borderError: Color,
    val borderSecondary: Color,

    val textPrimary: Color,
    val textSecondary: Color,
    val textWhite: Color,
    val textBrand: Color,

    val buttonPrimary: Color,
    val buttonSecondary: Color,
    val buttonDisabled: Color,

    val iconPrimary: Color,
    val iconSecondary: Color,
    val uconWhite: Color
)

val LocalAppColor = compositionLocalOf<AppColor> { error("App color is not provided") }