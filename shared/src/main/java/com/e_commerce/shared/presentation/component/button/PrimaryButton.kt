package com.e_commerce.shared.presentation.component.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.e_commerce.shared.presentation.FontSize
import com.e_commerce.shared.presentation.PreviewTheme
import com.e_commerce.shared.presentation.Resources

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes icon: Int? = null,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier,
        shape = RoundedCornerShape(6.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Resources.appColors.buttonPrimary,
            disabledContainerColor = Resources.appColors.buttonDisabled,
            contentColor = Resources.appColors.textPrimary,
            disabledContentColor = Resources.appColors.textPrimary.copy(alpha = 0.4f)
        ),
        contentPadding = PaddingValues(all = 20.dp)
    ) {
        icon?.let {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier.size(
                    14.dp
                )
            )

            Spacer(modifier = Modifier.width(12.dp))
        }

        Text(
            text = text,
            fontSize = FontSize.REGULAR,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
@Preview
private fun PrimaryButtonPrev() {
    PreviewTheme {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Update",
                onClick = {}
            )

            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Update",
                icon = Resources.Icon.Checkmark,
                onClick = {}
            )

            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Update",
                icon = Resources.Icon.Checkmark,
                enabled = false,
                onClick = {}
            )
        }
    }
}