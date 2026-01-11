package com.e_commerce.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.e_commerce.home.component.BottomAppBar
import com.e_commerce.home.model.BottomBarRoute
import com.e_commerce.shared.presentation.BebasNeueRegularFont
import com.e_commerce.shared.presentation.FontSize
import com.e_commerce.shared.presentation.Resources
import kotlinx.serialization.Serializable

@Serializable
object HomeGraph

@Serializable
object ProductOverview

@Serializable
object Cart

@Serializable
object Categories

fun NavGraphBuilder.homeGraph() {
    composable<HomeGraph> {
        Home()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Home() {
    val navController = rememberNavController()

    val backStack = navController.currentBackStackEntryAsState().value
    val selectedBottomBarRoute = remember(backStack) {
        val currentDestination = backStack?.destination
        BottomBarRoute.entries.find { bottomBarRoute ->
            bottomBarRoute.route.toString().contains(currentDestination?.route.toString())
        } ?: BottomBarRoute.ProductOverview
    }

    Scaffold(
        modifier = Modifier
            .fillMaxWidth()
            .systemBarsPadding(),

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
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(Resources.Icon.Menu),
                            contentDescription = null,
                        )
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
                Text("ProductOverview")
            }

            composable<Cart> {
                Text("Cart")
            }

            composable<Categories> {
                Text("Categories")
            }
        }
    }
}