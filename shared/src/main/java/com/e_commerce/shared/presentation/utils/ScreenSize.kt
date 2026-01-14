package com.e_commerce.shared.presentation.utils

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class ScreenSize(val width: Dp, val height: Dp) {
    companion object {
        @Composable
        fun getScreenSize(): ScreenSize {
            return ScreenSize(
                width = getScreenWidth(),
                height = getScreenHeight()
            )
        }

        @SuppressLint("ConfigurationScreenWidthHeight")
        @Composable
        fun getScreenWidth(): Dp {
            return LocalConfiguration.current.screenWidthDp.dp
        }

        @SuppressLint("ConfigurationScreenWidthHeight")
        @Composable
        fun getScreenHeight(): Dp {
            return LocalConfiguration.current.screenHeightDp.dp
        }
    }
}
