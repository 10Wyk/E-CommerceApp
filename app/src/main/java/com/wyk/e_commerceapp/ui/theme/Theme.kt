package com.wyk.e_commerceapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.e_commerce.shared.presentation.AppColor
import com.e_commerce.shared.presentation.Black
import com.e_commerce.shared.presentation.Gray
import com.e_commerce.shared.presentation.GrayDarker
import com.e_commerce.shared.presentation.GrayLighter
import com.e_commerce.shared.presentation.LocalAppColor
import com.e_commerce.shared.presentation.Orange
import com.e_commerce.shared.presentation.Red
import com.e_commerce.shared.presentation.White
import com.e_commerce.shared.presentation.Yellowish

private val lightColors = AppColor(
    surface = White,
    surfaceLighter = GrayLighter,
    surfaceDarker = Gray,
    surfaceBrand = Yellowish,
    surfaceError = Red,
    surfaceSecondary = Orange,
    borderIdle = GrayDarker,
    borderError = Red,
    borderSecondary = Orange,
    textPrimary = Black,
    textSecondary = Orange,
    textWhite = White,
    textBrand = Yellowish,
    buttonPrimary = Yellowish,
    buttonSecondary = GrayDarker,
    buttonDisabled = GrayDarker,
    iconPrimary = Black,
    iconSecondary = Orange,
    uconWhite = White
)

@Composable
fun ECommerceAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val appColor = when {
        darkTheme -> lightColors
        else -> lightColors
    }

    MaterialTheme(
        typography = Typography,
        content = {
            CompositionLocalProvider(
                LocalAppColor provides appColor
            ) {
                content.invoke()
            }
        }
    )
}