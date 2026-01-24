package com.e_commerce.shared.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.e_commerce.shared.presentation.PreviewTheme
import com.e_commerce.shared.presentation.Resources

@Composable
fun CircleCheckbox(
    checked: Boolean,
    checkedBgColor: Color = Resources.appColors.surfaceSecondary,
    unCheckedBgColor: Color = Resources.appColors.surfaceLighter,
    checkmarkColor: Color = Resources.appColors.iconWhite,
    onClick: (() -> Unit)? = null
) {
    val bgColor by animateColorAsState(
        targetValue = if (checked) checkedBgColor else unCheckedBgColor
    )

    Box(
        modifier = Modifier
            .size(20.dp)
            .clip(CircleShape)
            .background(color = bgColor)
            .clickable(
                enabled = onClick != null,
                onClick = {
                    onClick?.invoke()
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(visible = checked) {
            Icon(
                painter = painterResource(Resources.Icon.Checkmark),
                contentDescription = null,
                tint = checkmarkColor,
                modifier = Modifier.size(14.dp)
            )
        }
    }
}

@Preview
@Composable
private fun CircleCheckboxPrev() {
    PreviewTheme {
        Row(
            horizontalArrangement = Arrangement
                .spacedBy(12.dp),
            modifier = Modifier
                .background(
                    color = Color.Black.copy(alpha = 0.1f)
                )
                .padding(12.dp)
        ) {
            CircleCheckbox(checked = true)

            CircleCheckbox(checked = false)
        }
    }
}