package com.e_commerce.shared.presentation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.e_commerce.shared.R

@SuppressLint("ComposableNaming")
@Composable
fun BebasNeueRegularFont() = FontFamily(
    fonts = listOf(Font(resId = R.font.bebas_neue_regular, weight = FontWeight.W400))
)

@SuppressLint("ComposableNaming")
@Composable
fun RobotoCondensedMediumFont() = FontFamily(
    fonts = listOf(Font(resId = R.font.roboto_condensed_medium, weight = FontWeight.Medium))
)

object FontSize {
    val EXTRA_SMALL = 10.sp
    val SMALL = 12.sp
    val REGULAR = 14.sp
    val EXTRA_REGULAR = 16.sp
    val MEDIUM = 18.sp
    val EXTRA_MEDIUM = 20.sp
    val LARGE = 30.sp
    val EXTRA_LARGE = 40.sp
}
