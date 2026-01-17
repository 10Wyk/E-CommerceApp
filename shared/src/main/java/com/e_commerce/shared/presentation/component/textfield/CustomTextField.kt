package com.e_commerce.shared.presentation.component.textfield

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.e_commerce.shared.presentation.FontSize
import com.e_commerce.shared.presentation.PreviewTheme
import com.e_commerce.shared.presentation.Resources

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    enabled: Boolean = true,
    isError: Boolean = false,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text
    )
) {
    val colors = TextFieldDefaults.colors(
        unfocusedTextColor = Resources.appColors.textPrimary,
        focusedTextColor = Resources.appColors.textPrimary,
        disabledTextColor = Resources.appColors.textPrimary.copy(alpha = 0.4f),
        errorIndicatorColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        errorContainerColor = Resources.appColors.borderError.copy(alpha = 0.15f),
        disabledContainerColor = Resources.appColors.surfaceDarker,
        focusedContainerColor = Resources.appColors.surfaceLighter,
        unfocusedContainerColor = Resources.appColors.surfaceLighter
    )

    val borderColors by animateColorAsState(
        if (isError) Resources.appColors.borderError else Resources.appColors.borderIdle
    )

    val textStyle = LocalTextStyle.current

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .border(
                width = 1.dp,
                color = borderColors,
                shape = RoundedCornerShape(6.dp)
            ),
        enabled = enabled,
        isError = isError,
        singleLine = singleLine,
        colors = colors,
        keyboardOptions = keyboardOptions,
        shape = RoundedCornerShape(6.dp),
        textStyle = textStyle.copy(
            fontSize = FontSize.REGULAR
        ),
        placeholder = placeholder?.let {
            {
                Text(
                    text = placeholder,
                    color = Resources.appColors.textPrimary.copy(alpha = 0.5f),
                    fontSize = FontSize.REGULAR
                )
            }
        }
    )
}

@Composable
@Preview
private fun CustomTextFieldPrev() {
    PreviewTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            CustomTextField(
                value = "Hello, world!",
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {

                }
            )

            CustomTextField(
                value = "",
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {

                },
                placeholder = "Enter the name"
            )

            CustomTextField(
                value = "",
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {

                },
                isError = true
            )

            CustomTextField(
                value = "Hello, world!",
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {

                },
                enabled = false
            )
        }
    }
}