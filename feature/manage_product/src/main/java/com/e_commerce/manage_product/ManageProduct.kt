package com.e_commerce.manage_product

import ContentWithMessageBar
import MessageBarState
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import coil3.compose.SubcomposeAsyncImage
import com.e_commerce.manage_product.model.ManageProductAction
import com.e_commerce.manage_product.model.ManageProductEvent
import com.e_commerce.manage_product.model.ManageProductUiState
import com.e_commerce.shared.presentation.BebasNeueRegularFont
import com.e_commerce.shared.presentation.FontSize
import com.e_commerce.shared.presentation.PreviewTheme
import com.e_commerce.shared.presentation.Resources
import com.e_commerce.shared.presentation.component.InfoCard
import com.e_commerce.shared.presentation.component.button.PrimaryButton
import com.e_commerce.shared.presentation.component.dialog.CategoryPickerDialog
import com.e_commerce.shared.presentation.component.textfield.AlertTextField
import com.e_commerce.shared.presentation.component.textfield.CustomTextField
import com.e_commerce.shared.presentation.navigation.Screen
import com.e_commerce.shared.presentation.utils.DisplayResult
import com.e_commerce.shared.presentation.utils.ImageState
import com.e_commerce.shared.utils.collectAsOneTimeEvent
import rememberMessageBarState

fun NavGraphBuilder.manageProduct(
    navigateBack: () -> Unit
) = composable<Screen.ManageProduct> {
    val id = it.toRoute<Screen.ManageProduct>().id
    val viewModel: ManageProductViewModel = viewModel {
        ManageProductViewModel(id)
    }
    val state = viewModel.state.collectAsStateWithLifecycle().value

    val messageBarState = rememberMessageBarState()

    ManageProductView(
        state = state,
        update = id != null,
        action = viewModel::actionHandler,
        messageBarState = messageBarState
    )

    viewModel.eventFlow.collectAsOneTimeEvent { event ->
        when (event) {
            is ManageProductEvent.UpdateErrorMessage -> {
                messageBarState.addError(event.message)
            }

            is ManageProductEvent.UpdateSuccessMessage -> {
                messageBarState.addSuccess(event.message)
            }

            ManageProductEvent.NavigateBack -> navigateBack()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ManageProductView(
    state: ManageProductUiState,
    update: Boolean,
    messageBarState: MessageBarState,
    action: (ManageProductAction) -> Unit
) {
    val title = if (update) "edit product" else "new product"

    var dialogVisibility by remember { mutableStateOf(false) }

    if (dialogVisibility) CategoryPickerDialog(
        selectedCategory = state.productCategory,
        onDismiss = {
            dialogVisibility = false
        },
        onConfirmClick = {
            action(ManageProductAction.OnSelectProductCategory(it))
        }
    )

    //@formatter:off
    val imagePicker = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let { action(ManageProductAction.OnSelectImage(uri.toString())) } ?: action(
                ManageProductAction.OnSelectImage(null)
            )
    }
    //@formatter:on

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
                        text = title.uppercase(),
                        fontFamily = BebasNeueRegularFont(),
                        fontSize = FontSize.LARGE
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            action(ManageProductAction.OnNavigateBackClick)
                        },
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
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Resources.appColors.surface),
                onLoading = {
                    CircularProgressIndicator(
                        color = Resources.appColors.iconSecondary
                    )
                },
                onError = {
                    InfoCard(
                        modifier = Modifier.fillMaxSize(),
                        title = "Oops!",
                        subTitle = state.requestState.getErrorMessage(),
                        image = Resources.Image.Cat
                    )
                },
                onSuccess = {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Resources.appColors.surface)
                            .padding(horizontal = 24.dp)
                            .imePadding()
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .verticalScroll(rememberScrollState())
                                .padding(top = 12.dp, bottom = 24.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            AnimatedContent(
                                targetState = state.imageState
                            ) { state ->
                                when (state) {
                                    ImageState.Idle, ImageState.Error -> {
                                        Button(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(300.dp),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Resources.appColors.surfaceLighter,
                                                contentColor = Resources.appColors.textPrimary
                                            ),
                                            shape = RoundedCornerShape(12.dp),
                                            border = BorderStroke(
                                                width = 1.dp,
                                                color = Resources.appColors.surfaceDarker
                                            ),
                                            onClick = {
                                                imagePicker.launch(PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly))
                                            }
                                        ) {
                                            Icon(
                                                painter = painterResource(Resources.Icon.Plus),
                                                contentDescription = null,
                                                modifier = Modifier.size(24.dp),
                                                tint = Resources.appColors.iconPrimary
                                            )
                                        }
                                    }

                                    ImageState.Loading -> {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(300.dp)
                                                .background(color = Resources.appColors.surfaceLighter)
                                                .clip(shape = RoundedCornerShape(12.dp))
                                                .border(
                                                    border = BorderStroke(
                                                        width = 1.dp,
                                                        color = Resources.appColors.surfaceDarker
                                                    )
                                                ),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            CircularProgressIndicator(
                                                color = Resources.appColors.iconSecondary
                                            )
                                        }
                                    }

                                    is ImageState.Success -> {
                                        SubcomposeAsyncImage(
                                            model = state.imageUrl,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(300.dp)
                                                .clickable(
                                                    enabled = true,
                                                    onClick = {
                                                        imagePicker.launch(
                                                            PickVisualMediaRequest(
                                                                mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                                                            )
                                                        )
                                                    },
                                                    interactionSource = null,
                                                    indication = ripple(bounded = true)
                                                ),
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                }
                            }

                            CustomTextField(
                                value = state.title,
                                placeholder = "Title",
                                onValueChange = {
                                    action(ManageProductAction.OnChangeTitle(it))
                                },
                                modifier = Modifier.fillMaxWidth()
                            )

                            CustomTextField(
                                value = state.description,
                                singleLine = false,
                                placeholder = "Description",
                                onValueChange = {
                                    action(ManageProductAction.OnChangeDescription(it))
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(168.dp)
                            )

                            AlertTextField(
                                modifier = Modifier.fillMaxWidth(),
                                text = state.productCategory.title,
                                onClick = {
                                    dialogVisibility = true
                                }
                            )

                            CustomTextField(
                                value = state.weight.orEmpty(),
                                singleLine = false,
                                placeholder = "Weight",
                                onValueChange = {
                                    action(ManageProductAction.OnChangeWeight(it))
                                },
                                modifier = Modifier.fillMaxWidth()
                            )

                            CustomTextField(
                                value = state.flavors.orEmpty(),
                                singleLine = false,
                                placeholder = "Flavors",
                                onValueChange = {
                                    action(ManageProductAction.OnChangeFlavors(it))
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                            )

                            CustomTextField(
                                value = state.price,
                                singleLine = false,
                                placeholder = "Price",
                                onValueChange = {
                                    action(ManageProductAction.OnChangePrice(it))
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = "New",
                                    fontSize = FontSize.REGULAR,
                                    color = Resources.appColors.textPrimary
                                )

                                Spacer(modifier = Modifier.weight(1f))

                                Switch(
                                    modifier = Modifier
                                        .height(32.dp)
                                        .width(52.dp),
                                    checked = state.new,
                                    onCheckedChange = {
                                        action(ManageProductAction.ToggleNew)
                                    },
                                    colors = SwitchDefaults.colors(
                                        checkedTrackColor = Resources.appColors.surfaceSecondary,
                                        checkedThumbColor = Resources.appColors.surface,
                                        uncheckedThumbColor = Resources.appColors.surface,
                                        uncheckedTrackColor = Resources.appColors.surfaceDarker,
                                        checkedBorderColor = Color.Transparent,
                                        uncheckedBorderColor = Color.Transparent
                                    )
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = "Popular",
                                    fontSize = FontSize.REGULAR,
                                    color = Resources.appColors.textPrimary
                                )

                                Spacer(modifier = Modifier.weight(1f))

                                Switch(
                                    modifier = Modifier
                                        .height(32.dp)
                                        .width(52.dp),
                                    checked = state.popular,
                                    onCheckedChange = {
                                        action(ManageProductAction.TogglePopular)
                                    },
                                    colors = SwitchDefaults.colors(
                                        checkedTrackColor = Resources.appColors.surfaceSecondary,
                                        checkedThumbColor = Resources.appColors.surface,
                                        uncheckedThumbColor = Resources.appColors.surface,
                                        uncheckedTrackColor = Resources.appColors.surfaceDarker,
                                        checkedBorderColor = Color.Transparent,
                                        uncheckedBorderColor = Color.Transparent
                                    )
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = "Discounted",
                                    fontSize = FontSize.REGULAR,
                                    color = Resources.appColors.textPrimary
                                )

                                Spacer(modifier = Modifier.weight(1f))

                                Switch(
                                    modifier = Modifier
                                        .height(32.dp)
                                        .width(52.dp),
                                    checked = state.discounted,
                                    onCheckedChange = {
                                        action(ManageProductAction.ToggleDiscounted)
                                    },
                                    colors = SwitchDefaults.colors(
                                        checkedTrackColor = Resources.appColors.surfaceSecondary,
                                        checkedThumbColor = Resources.appColors.surface,
                                        uncheckedThumbColor = Resources.appColors.surface,
                                        uncheckedTrackColor = Resources.appColors.surfaceDarker,
                                        checkedBorderColor = Color.Transparent,
                                        uncheckedBorderColor = Color.Transparent
                                    )
                                )
                            }
                        }

                        val buttonText = if (update) "Update" else "Add new product"
                        val icon = if (update) Resources.Icon.Checkmark else Resources.Icon.Plus

                        PrimaryButton(
                            modifier = Modifier
                                .padding(bottom = 24.dp)
                                .fillMaxWidth(),
                            onClick = {
                                action(ManageProductAction.OnUpsertButtonClick)
                            },
                            text = buttonText,
                            icon = icon
                        )
                    }
                }
            )
        }
    }
}

@Composable
@Preview
private fun ManageProductViewPrev() {
    PreviewTheme {
        ManageProductView(
            state = ManageProductUiState(),
            update = false,
            action = {},
            messageBarState = rememberMessageBarState()
        )
    }
}