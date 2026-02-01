package com.e_commerce.shared.presentation.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.e_commerce.shared.presentation.BebasNeueRegularFont
import com.e_commerce.shared.presentation.Resources

@Composable
fun InfoCard(
    modifier: Modifier = Modifier,
    @DrawableRes image: Int,
    title: String,
    subTitle: String? = null
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(image),
                contentDescription = title,
                modifier = Modifier.size(60.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = title,
                fontFamily = BebasNeueRegularFont(),
                color = Resources.appColors.textPrimary,
                fontWeight = FontWeight.W600,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )

            subTitle?.let {
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = subTitle,
                    fontFamily = BebasNeueRegularFont(),
                    color = Resources.appColors.textPrimary,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}