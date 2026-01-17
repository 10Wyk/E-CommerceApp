package com.e_commerce.home

import ContentWithMessageBar
import MessageBarState
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.e_commerce.home.component.BottomAppBar
import com.e_commerce.home.component.CustomNavigationDrawer
import com.e_commerce.home.model.BottomBarRoute
import com.e_commerce.home.model.DrawerState
import com.e_commerce.home.model.HomeAction
import com.e_commerce.home.model.HomeEvent
import com.e_commerce.home.model.HomeUiState
import com.e_commerce.shared.presentation.BebasNeueRegularFont
import com.e_commerce.shared.presentation.FontSize
import com.e_commerce.shared.presentation.Resources
import com.e_commerce.shared.presentation.utils.ScreenSize
import com.e_commerce.shared.presentation.utils.onSwipeLeft
import com.e_commerce.shared.utils.collectAsOneTimeEvent
import com.e_commerce.shared.utils.ifNotBlank
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable
import rememberMessageBarState

@Serializable
object HomeGraph

@Serializable
object ProductOverview

@Serializable
object Cart

@Serializable
object Categories

fun NavGraphBuilder.homeGraph(
    navigateToAuth: () -> Unit
) {
    composable<HomeGraph> {
        val viewModel: HomeViewModel = viewModel()
        val state = viewModel.state.collectAsStateWithLifecycle().value
        val messageBarState = rememberMessageBarState()

        Home(
            state = state,
            messageBarState = messageBarState,
            action = viewModel::actionHandler
        )

        viewModel.eventState.collectAsOneTimeEvent { event ->
            when (event) {
                is HomeEvent.UpdateErrorMessage -> {
                    messageBarState.addError(event.message)
                }

                is HomeEvent.UpdateSuccessMessage -> {
                    event.message.ifNotBlank {
                        messageBarState.addSuccess(event.message)
                        delay(500)
                    }
                    navigateToAuth.invoke()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Home(
    state: HomeUiState,
    messageBarState: MessageBarState,
    action: (HomeAction) -> Unit
) {
    val navController = rememberNavController()

    val backStack = navController.currentBackStackEntryAsState().value
    val selectedBottomBarRoute = remember(backStack) {
        val currentDestination = backStack?.destination
        BottomBarRoute.entries.find { bottomBarRoute ->
            bottomBarRoute.route.toString().contains(currentDestination?.route.toString())
        } ?: BottomBarRoute.ProductOverview
    }

    val screenWidth = ScreenSize.getScreenWidth()
    val offset by remember { derivedStateOf { (screenWidth.value / 1.5f).dp } }

    val animatedOffset by animateDpAsState(
        targetValue = when (state.drawerState) {
            DrawerState.Closed -> 0.dp
            DrawerState.Opened -> offset
        }
    )

    val animatedScale by animateFloatAsState(
        targetValue = when (state.drawerState) {
            DrawerState.Closed -> 1f
            DrawerState.Opened -> 0.9f
        }
    )

    val animateCorner by animateDpAsState(
        targetValue = when (state.drawerState) {
            DrawerState.Closed -> 0.dp
            DrawerState.Opened -> 20.dp
        }
    )

    val animatedBgColor by animateColorAsState(
        targetValue = when (state.drawerState) {
            DrawerState.Closed -> Resources.appColors.surface
            DrawerState.Opened -> Resources.appColors.surfaceLighter
        }
    )

    BackHandler(state.drawerState.isOpened()) {
        action(HomeAction.OnToggleDrawer)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = animatedBgColor)
            .systemBarsPadding()
    ) {
        CustomNavigationDrawer(
            onAdminClick = {},
            onContactClick = {},
            onProfileClick = {},
            onSignOutClick = {
                action(HomeAction.OnSignOutClick)
            }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(animateCorner))
                .scale(scale = animatedScale)
                .offset(x = animatedOffset)
                .shadow(
                    elevation = 20.dp,
                    shape = RoundedCornerShape(animateCorner),
                    ambientColor = Color.Black.copy(alpha = 0.4f),
                    spotColor = Color.Black.copy(alpha = 0.4f)
                )
                .pointerInput(state.drawerState) {
                    detectTapGestures(
                        onTap = {
                            if (state.drawerState.isOpened()) action(HomeAction.OnToggleDrawer)
                        }
                    )
                }
                .onSwipeLeft {
                    action(HomeAction.OnToggleDrawer)
                }
        ) {

            ContentWithMessageBar(
                modifier = Modifier
                    .fillMaxSize(),
                messageBarState = messageBarState,
                errorMaxLines = 2,
                errorContainerColor = Resources.appColors.surfaceError,
                errorContentColor = Resources.appColors.textWhite,
                successContainerColor = Resources.appColors.surfaceBrand,
                successContentColor = Resources.appColors.textPrimary
            ) {
                Scaffold(
                    modifier = Modifier
                        .background(color = Resources.appColors.surface)
                        .fillMaxSize(),
                    topBar = {
                        CenterAlignedTopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Resources.appColors.surface,
                                navigationIconContentColor = Resources.appColors.iconPrimary,
                                titleContentColor = Resources.appColors.textPrimary
                            ),
                            title = {
                                AnimatedContent(
                                    targetState = selectedBottomBarRoute,
                                    transitionSpec = {
                                        if (targetState.ordinal > initialState.ordinal) {
                                            slideInVertically { height -> height } togetherWith
                                                    slideOutVertically { height -> -height }
                                        } else {
                                            slideInVertically { height -> -height } togetherWith
                                                    slideOutVertically { height -> height }
                                        }
                                    },
                                    contentAlignment = Alignment.Center
                                ) { route ->
                                    Text(
                                        text = route.title,
                                        fontFamily = BebasNeueRegularFont(),
                                        fontSize = FontSize.LARGE
                                    )
                                }
                            },
                            navigationIcon = {
                                AnimatedContent(targetState = state.drawerState) { state ->
                                    when (state) {
                                        DrawerState.Closed -> IconButton(onClick = {
                                            action(HomeAction.OnToggleDrawer)
                                        }) {
                                            Icon(
                                                painter = painterResource(Resources.Icon.Menu),
                                                contentDescription = null,
                                            )
                                        }

                                        DrawerState.Opened -> IconButton(onClick = {
                                            action(HomeAction.OnToggleDrawer)
                                        }) {
                                            Icon(
                                                painter = painterResource(Resources.Icon.Close),
                                                contentDescription = null,
                                            )
                                        }
                                    }
                                }
                            }
                        )
                    },
                    bottomBar = {
                        BottomAppBar(
                            modifier = Modifier
                                .background(color = Resources.appColors.surface)
                                .padding(12.dp),
                            selectedBottomBarRoute = selectedBottomBarRoute,
                            onSelect = { route ->
                                navController.navigate(route.route) {
                                    popUpTo(BottomBarRoute.ProductOverview.route) {
                                        saveState = true
                                    }
                                    restoreState = true
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    NavHost(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                            .background(color = Resources.appColors.surface),
                        navController = navController,
                        startDestination = ProductOverview
                    ) {
                        composable<ProductOverview> {
                        }

                        composable<Cart> {
                        }

                        composable<Categories> {
                        }
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = state.loadingState,
            modifier = Modifier.align(Alignment.Center)
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(34.dp),
                color = Resources.appColors.iconSecondary,
                strokeWidth = 2.dp,
                strokeCap = StrokeCap.Round
            )
        }
    }
}