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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.e_commerce.auth.component.GoogleSignUpButton
import com.e_commerce.shared.presentation.BebasNeueRegularFont
import com.e_commerce.shared.presentation.FontSize
import com.e_commerce.shared.presentation.PreviewTheme
import com.e_commerce.shared.presentation.Resources
import com.e_commerce.shared.presentation.RobotoCondensedMediumFont
import com.mmk.kmpauth.google.GoogleButtonUiContainer
import kotlinx.serialization.Serializable
import rememberMessageBarState

@Serializable
object AuthScreen

fun NavGraphBuilder.auth() {
    composable<AuthScreen> {
        AuthView()
    }
}

@Composable
private fun AuthView() {
    val messageBarState = rememberMessageBarState()
    var loading by remember { mutableStateOf(false) }

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

                GoogleButtonUiContainer(
                    filterByAuthorizedAccounts = false,
                    onGoogleSignInResult = { googleUser ->
                        loading = false
                        if (googleUser == null)
                            messageBarState.addError("Unable to login with google account")
                        else
                            messageBarState.addSuccess("Authentication success")
                    }
                ) {
                    GoogleSignUpButton(
                        modifier = Modifier
                            .padding(bottom = 24.dp),
                        loading = loading
                    ) {
                        loading = true
                        this.onClick()
                    }
                }
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