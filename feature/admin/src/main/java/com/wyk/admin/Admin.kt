package com.wyk.admin

import ContentWithMessageBar
import MessageBarState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.e_commerce.shared.presentation.BebasNeueRegularFont
import com.e_commerce.shared.presentation.FontSize
import com.e_commerce.shared.presentation.Resources
import com.e_commerce.shared.presentation.component.product.ProductCard
import com.e_commerce.shared.presentation.navigation.Screen
import com.e_commerce.shared.presentation.utils.DisplayResult
import com.e_commerce.shared.utils.collectAsOneTimeEvent
import com.wyk.admin.model.AdminAction
import com.wyk.admin.model.AdminEvent
import com.wyk.admin.model.AdminUiState
import rememberMessageBarState

fun NavGraphBuilder.admin(
    navigateBack: () -> Unit,
    navigateToManageProduct: (String?) -> Unit
) {
    composable<Screen.Admin> {
        val viewModel: AdminViewModel = viewModel()
        val state = viewModel.state.collectAsStateWithLifecycle().value
        val messageBarState = rememberMessageBarState()

        Admin(
            state = state,
            messageBarState = messageBarState,
            action = viewModel::actionHandler
        )

        viewModel.eventFlow.collectAsOneTimeEvent { event ->
            when (event) {
                AdminEvent.NavigateBack -> navigateBack()
                is AdminEvent.NavigateToManageProduct -> navigateToManageProduct(event.id)
                is AdminEvent.UpdateErrorMessage -> {
                    messageBarState.addError(event.message)
                }

                is AdminEvent.UpdateSuccessMessage -> {
                    messageBarState.addSuccess(event.message)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Admin(
    state: AdminUiState,
    messageBarState: MessageBarState,
    action: (AdminAction) -> Unit
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
                        text = "admin panel".uppercase(),
                        fontFamily = BebasNeueRegularFont(),
                        fontSize = FontSize.LARGE
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            action(AdminAction.OnNavigateBackClick)
                        },
                    ) {
                        Icon(
                            painter = painterResource(Resources.Icon.BackArrow),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            painter = painterResource(Resources.Icon.Search),
                            modifier = Modifier.size(24.dp),
                            contentDescription = null
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    action(AdminAction.OnNavigateToManageProductClick(id = null))
                },
                containerColor = Resources.appColors.buttonPrimary,
                contentColor = Resources.appColors.iconPrimary
            ) {
                Icon(
                    painter = painterResource(Resources.Icon.Plus),
                    modifier = Modifier.size(24.dp),
                    contentDescription = null
                )
            }
        }
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
            state.requestState.DisplayResult(
                onSuccess = { products ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Resources.appColors.surface)
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 8.dp)
                                .padding(top = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(
                                items = products,
                                key = { product ->
                                    product.id
                                }
                            ) { product ->
                                ProductCard(
                                    product = product
                                ) {
                                    action(AdminAction.OnNavigateToManageProductClick(it))
                                }
                            }
                        }
                    }
                }
            )
        }
    }
}