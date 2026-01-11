package com.e_commerce.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.e_commerce.home.component.BottomAppBar
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

@Composable
private fun Home() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier
            .fillMaxWidth()
            .systemBarsPadding(),
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .background(color = Resources.appColors.surface)
                    .padding(12.dp),
                navController = navController
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