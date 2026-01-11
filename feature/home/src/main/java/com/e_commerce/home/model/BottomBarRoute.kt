package com.e_commerce.home.model

import com.e_commerce.shared.presentation.Resources

enum class BottomBarRoute(
    val icon: Int,
    val title: String,
    val route: Any
) {
    ProductOverview(
        icon = Resources.Icon.Home,
        title = "Nutri Sport",
        route = com.e_commerce.home.ProductOverview
    ),
    Cart(
        icon = Resources.Icon.ShoppingCart,
        title = "Cart",
        route = com.e_commerce.home.Cart
    ),
    Categories(
        icon = Resources.Icon.Categories,
        title = "Categories",
        route = com.e_commerce.home.Categories
    );
}