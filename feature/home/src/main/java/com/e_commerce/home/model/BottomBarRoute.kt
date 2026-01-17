package com.e_commerce.home.model

import com.e_commerce.shared.presentation.Resources
import com.e_commerce.shared.presentation.navigation.Screen

enum class BottomBarRoute(
    val icon: Int,
    val title: String,
    val route: Any
) {
    ProductOverview(
        icon = Resources.Icon.Home,
        title = "Nutri Sport",
        route = Screen.ProductOverview
    ),
    Cart(
        icon = Resources.Icon.ShoppingCart,
        title = "Cart",
        route = Screen.Cart
    ),
    Categories(
        icon = Resources.Icon.Categories,
        title = "Categories",
        route = Screen.Categories
    );
}