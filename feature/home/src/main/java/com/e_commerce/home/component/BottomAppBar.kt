package com.e_commerce.home.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.e_commerce.home.model.BottomBarRoute
import com.e_commerce.shared.presentation.PreviewTheme
import com.e_commerce.shared.presentation.Resources

@Composable
internal fun BottomAppBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    badgedRoute: BottomBarRoute? = null
) {
    val backStack = navController.currentBackStackEntryAsState().value
    val selectedBottomBarRoute = remember(backStack) {
        val currentDestination = backStack?.destination
        BottomBarRoute.entries.find { bottomBarRoute ->
            bottomBarRoute.route.toString().contains(currentDestination?.route.toString())
        } ?: BottomBarRoute.ProductOverview
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(color = Resources.appColors.surfaceLighter)
            .padding(horizontal = 36.dp, vertical = 12.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BottomBarRoute.entries.forEach { route ->
                val animatedTint by animateColorAsState(
                    targetValue = if (selectedBottomBarRoute == route) Resources.appColors.iconSecondary else Resources.appColors.iconPrimary
                )

                IconButton(
                    onClick = {
                        navController.navigate(route.route) {
                            popUpTo(BottomBarRoute.ProductOverview.route) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    },
                    shape = RoundedCornerShape(9.dp)
                ) {
                    Icon(
                        painter = painterResource(route.icon),
                        contentDescription = route.title,
                        tint = animatedTint,
                        modifier = Modifier
                            .size(24.dp)
                    )

                    AnimatedVisibility(
                        visible = badgedRoute == route
                    ) {
                        Box(
                            modifier = Modifier
                                .offset(y = (-12.5).dp, x = 12.dp)
                                .clip(CircleShape)
                                .size(8.dp)
                                .background(color = Resources.appColors.surfaceSecondary)
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
private fun BottomAppBarPrev() {
    PreviewTheme {
        Box(
            modifier = Modifier.background(
                color = Resources.appColors.surface
            )
        ) {
            BottomAppBar(
                modifier = Modifier.padding(horizontal = 12.dp),
                navController = rememberNavController(),
                badgedRoute = BottomBarRoute.Cart
            )
        }
    }
}