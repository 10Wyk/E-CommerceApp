package com.e_commerce.shared

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

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
fun PreviewTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val appColor = when {
        darkTheme -> lightColors
        else -> lightColors
    }

    CompositionLocalProvider(
        LocalAppColor provides appColor
    ) {
        Surface {
            content.invoke()
        }
    }
}