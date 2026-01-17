package com.e_commerce.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.e_commerce.profile.model.ProfileAction
import com.e_commerce.profile.model.ProfileUiState
import com.e_commerce.shared.presentation.BebasNeueRegularFont
import com.e_commerce.shared.presentation.FontSize
import com.e_commerce.shared.presentation.Resources
import com.e_commerce.shared.presentation.component.button.PrimaryButton
import com.e_commerce.shared.presentation.component.textfield.CustomTextField
import com.e_commerce.shared.presentation.navigation.Screen

fun NavGraphBuilder.profile() {
    composable<Screen.Profile> {
        Profile()
    }
}

@Composable
private fun Profile() {
    val viewModel: ProfileViewModel = viewModel()
    val state = viewModel.state.collectAsStateWithLifecycle().value

    ProfileView(
        state = state,
        action = viewModel::actonHandler
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileView(
    state: ProfileUiState,
    action: (ProfileAction) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Resources.appColors.surface)
            .systemBarsPadding(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Resources.appColors.surface,
                    navigationIconContentColor = Resources.appColors.iconPrimary,
                    titleContentColor = Resources.appColors.textPrimary
                ),
                title = {
                    Text(
                        text = "My Profile".uppercase(),
                        fontFamily = BebasNeueRegularFont(),
                        fontSize = FontSize.LARGE
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {},
                    ) {
                        Icon(
                            painter = painterResource(Resources.Icon.BackArrow),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .background(color = Resources.appColors.surface)
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(top = 12.dp, bottom = 24.dp)
                .verticalScroll(state = rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CustomTextField(
                value = state.firstName,
                onValueChange = {
                    action(ProfileAction.OnChangeFirstName(it))
                },
                modifier = Modifier.fillMaxWidth()
            )

            CustomTextField(
                value = state.lastName,
                onValueChange = {
                    action(ProfileAction.OnChangeLastName(it))
                },
                modifier = Modifier.fillMaxWidth()
            )

            CustomTextField(
                value = state.email,
                onValueChange = {},
                enabled = false,
                modifier = Modifier.fillMaxWidth()
            )

            CustomTextField(
                value = state.city,
                onValueChange = {
                    action(ProfileAction.OnChangeCity(it))
                },
                modifier = Modifier.fillMaxWidth()
            )

            CustomTextField(
                value = state.postalCode,
                onValueChange = {
                    action(ProfileAction.OnChangePostalCode(it))
                },
                modifier = Modifier.fillMaxWidth()
            )

            CustomTextField(
                value = state.address,
                onValueChange = {
                    action(ProfileAction.OnChangeAddress(it))
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1f))

            PrimaryButton(
                text = "Update",
                modifier = Modifier.fillMaxWidth(),
                icon = Resources.Icon.Checkmark
            ) { }
        }
    }
}

