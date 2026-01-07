package com.e_commerce.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.e_commerce.auth.AuthScreen
import com.e_commerce.auth.auth

@Composable
fun AppNavController(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = AuthScreen
    ) {
        auth()
    }
}