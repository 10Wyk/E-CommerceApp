package com.e_commerce.shared.presentation.component.textfield

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.e_commerce.shared.presentation.FontSize
import com.e_commerce.shared.presentation.PreviewTheme
import com.e_commerce.shared.presentation.Resources

@Composable
fun AlertTextField(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes icon: Int? = null,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    val color by animateColorAsState(
        targetValue = if (enabled) Resources.appColors.textPrimary else Resources.appColors.textPrimary.copy(
            alpha = 0.4f
        )
    )

    Row(
        modifier = modifier
            .background(color = Resources.appColors.surfaceLighter)
            .border(
                width = 1.dp,
                color = Resources.appColors.borderIdle,
                shape = RoundedCornerShape(6.dp)
            )
            .clip(shape = RoundedCornerShape(6.dp))
            .clickable(
                enabled = enabled,
                onClick = onClick
            )
            .padding(vertical = 20.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon?.let {
            Image(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier.size(14.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))
        }

        Text(
            text = text,
            color = color,
            fontSize = FontSize.REGULAR
        )
    }
}

@Composable
@Preview
private fun AlertTextFieldPrev() {
    PreviewTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            AlertTextField(
                text = "+338",
                onClick = {

                }
            )

            AlertTextField(
                text = "+338",
                icon = Resources.Flag.Serbia,
                onClick = {

                }
            )

            AlertTextField(
                text = "+338",
                icon = Resources.Flag.Serbia,
                enabled = false,
                onClick = {

                }
            )
        }
    }
}