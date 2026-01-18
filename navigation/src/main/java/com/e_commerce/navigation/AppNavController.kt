package com.e_commerce.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.e_commerce.auth.auth
import com.e_commerce.home.homeGraph
import com.e_commerce.profile.profile
import com.e_commerce.shared.domain.repository.CustomerRepository
import com.e_commerce.shared.presentation.navigation.Screen
import org.koin.compose.koinInject

@Composable
fun AppNavController(
    modifier: Modifier = Modifier
) {
    val customerRepository = koinInject<CustomerRepository>()
    val navController = rememberNavController()
    val isUserAuthenticated by remember {
        mutableStateOf(customerRepository.currentUserId() != null)
    }
    val startDestination = remember {
        if (isUserAuthenticated) Screen.HomeGraph else Screen.Auth
    }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        auth {
            navController.navigate(Screen.HomeGraph) {
                popUpTo(Screen.Auth) {
                    inclusive = true
                }
            }
        }
        homeGraph(
            navigateToAuth = {
                navController.navigate(Screen.Auth) {
                    popUpTo(Screen.HomeGraph) {
                        inclusive = true
                    }
                }
            },
            navigateToProfile = {
                navController.navigate(Screen.Profile)
            }
        )
        profile {
            navController.popBackStack()
        }
    }
}