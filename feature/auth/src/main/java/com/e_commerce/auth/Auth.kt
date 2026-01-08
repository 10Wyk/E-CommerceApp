package com.e_commerce.auth

import ContentWithMessageBar
import MessageBarState
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.e_commerce.auth.component.GoogleSignUpButton
import com.e_commerce.auth.model.AuthAction
import com.e_commerce.auth.model.AuthEvent
import com.e_commerce.shared.presentation.BebasNeueRegularFont
import com.e_commerce.shared.presentation.FontSize
import com.e_commerce.shared.presentation.PreviewTheme
import com.e_commerce.shared.presentation.Resources
import com.e_commerce.shared.presentation.RobotoCondensedMediumFont
import com.e_commerce.shared.util.collectAsOneTimeEvent
import com.mmk.kmpauth.firebase.google.GoogleButtonUiContainerFirebase
import kotlinx.serialization.Serializable
import rememberMessageBarState

@Serializable
object AuthScreen

fun NavGraphBuilder.auth() {
    composable<AuthScreen> {
        val viewModel = viewModel { AuthViewModel() }
        val loadingState = viewModel.state.collectAsStateWithLifecycle().value
        val messageBarState = rememberMessageBarState()

        AuthView(
            loadingState,
            messageBarState,
            action = viewModel::actionHandler
        )

        viewModel.eventFlow.collectAsOneTimeEvent { event ->
            when (event) {
                is AuthEvent.UpdateErrorMessage -> {
                    messageBarState.addError(event.message)
                }

                is AuthEvent.UpdateSuccessMessage -> {
                    messageBarState.addSuccess(event.message)
                }

                null -> {}
            }
        }
    }
}

@Composable
private fun AuthView(
    loadingState: Boolean,
    messageBarState: MessageBarState,
    action: (AuthAction) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { contentPadding ->
        ContentWithMessageBar(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize(),
            messageBarState = messageBarState,
            errorMaxLines = 2,
            errorContainerColor = Resources.appColors.surfaceError,
            errorContentColor = Resources.appColors.textWhite,
            successContainerColor = Resources.appColors.surfaceBrand,
            successContentColor = Resources.appColors.textPrimary
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

                GoogleButtonUiContainerFirebase(
                    filterByAuthorizedAccounts = false,
                    linkAccount = false,
                    onResult = { result ->
                        action(AuthAction.OnSignUpResult(result))
                    }
                ) {
                    GoogleSignUpButton(
                        modifier = Modifier
                            .padding(bottom = 24.dp),
                        loading = loadingState
                    ) {
                        action(AuthAction.OnSingUpClick)
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
        AuthView(
            loadingState = false,
            messageBarState = rememberMessageBarState(),
            action = {}
        )
    }
}