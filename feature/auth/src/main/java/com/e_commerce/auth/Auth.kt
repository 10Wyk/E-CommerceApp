package com.e_commerce.auth

import ContentWithMessageBar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.e_commerce.auth.component.GoogleSignUpButton
import com.e_commerce.shared.BebasNeueRegularFont
import com.e_commerce.shared.FontSize
import com.e_commerce.shared.PreviewTheme
import com.e_commerce.shared.Resources
import com.e_commerce.shared.RobotoCondensedMediumFont
import rememberMessageBarState

@Composable
fun Auth() {
    viewModel {
        AuthViewModel()
    }

    AuthView()
}

@Composable
private fun AuthView() {
    val messageBarState = rememberMessageBarState()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { contentPadding ->
        ContentWithMessageBar(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize(),
            messageBarState = messageBarState,
            errorMaxLines = 2
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "nutrisport".uppercase(),
                        color = Resources.appColors.textSecondary,
                        fontSize = FontSize.EXTRA_LARGE,
                        fontFamily = BebasNeueRegularFont(),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Sign in to continue",
                        color = Resources.appColors.textPrimary.copy(alpha = 0.5f),
                        fontSize = FontSize.EXTRA_REGULAR,
                        fontFamily = RobotoCondensedMediumFont(),
                        textAlign = TextAlign.Center
                    )
                }

                GoogleSignUpButton { }
            }
        }
    }
}

@Composable
@Preview
private fun AuthViewPrev() {
    PreviewTheme {
        AuthView()
    }
}