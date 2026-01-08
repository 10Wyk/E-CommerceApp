package com.e_commerce.auth.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.e_commerce.shared.presentation.FontSize
import com.e_commerce.shared.presentation.PreviewTheme
import com.e_commerce.shared.presentation.Resources
import com.e_commerce.shared.presentation.RobotoCondensedMediumFont

@Composable
fun GoogleSignUpButton(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    primaryText: String = "Sign in with Google",
    secondaryText: String = "Please wait...",
    icon: Int = Resources.Image.GoogleLogo,
    shape: Shape = RoundedCornerShape(99.dp),
    backgroundColor: Color = Resources.appColors.surfaceDarker,
    borderColor: Color = Resources.appColors.borderIdle,
    progressIndicatorColor: Color = Resources.appColors.iconSecondary,
    onClick: () -> Unit
) {
    var buttonText by remember { mutableStateOf(primaryText) }

    LaunchedEffect(loading) {
        buttonText = if (loading) secondaryText else primaryText
    }

    Surface(
        modifier = modifier,
        shape = shape,
        onClick = onClick,
        border = BorderStroke(
            width = 1.dp,
            color = borderColor
        ),
        enabled = !loading,
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (!loading) Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(icon),
                contentDescription = null
            ) else CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = progressIndicatorColor,
                trackColor = Color.Transparent,
                strokeCap = StrokeCap.Round,
                strokeWidth = 2.dp
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = buttonText,
                fontFamily = RobotoCondensedMediumFont(),
                fontSize = FontSize.REGULAR
            )
        }
    }
}

@Preview
@Composable
private fun GoogleSignUpButtonPrev() {
    PreviewTheme {
        GoogleSignUpButton { }
    }
}
