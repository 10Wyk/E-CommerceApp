package com.e_commerce.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.e_commerce.auth.AuthScreen
import com.e_commerce.auth.auth
import com.e_commerce.home.HomeGraph
import com.e_commerce.home.homeGraph
import com.e_commerce.shared.domain.repository.CustomerRepository
import org.koin.compose.koinInject

@Composable
fun AppNavController(
    modifier: Modifier = Modifier
) {
    val customerRepository = koinInject<CustomerRepository>()
    val navController = rememberNavController()
    val isUserAuthenticated by remember {
        Log.d("Current user data", customerRepository.currentUserId().toString())
        mutableStateOf(customerRepository.currentUserId() != null)
    }
    val startDestination = remember {
        if (isUserAuthenticated) HomeGraph else AuthScreen
    }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        auth {
            navController.navigate(HomeGraph) {
                popUpTo(AuthScreen) {
                    inclusive = true
                }
            }
        }
        homeGraph()
    }
}