package com.e_commerce.home.model

import com.e_commerce.shared.presentation.Resources

enum class DrawerItem(
    val title: String,
    val icon: Int
) {
    Profile(
        title = "Profile",
        icon = Resources.Icon.Person
    ),
    Blog(
        title = "Blog",
        icon = Resources.Icon.Book
    ),
    Location(
        title = "Location",
        icon = Resources.Icon.MapPin
    ),
    Contact(
        title = "Contact Us",
        icon = Resources.Icon.Edit
    ),
    SingOut(
        title = "Sign out",
        icon = Resources.Icon.SignOut
    ),
    Admin(
        title = "Admin panel",
        icon = Resources.Icon.Unlock
    )
}