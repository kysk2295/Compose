package com.example.compose

import androidx.annotation.DrawableRes

sealed class Screen(val route:String, val title: String, @DrawableRes val icon:Int){
    object Chat : Screen(route = "chat",title = "Chat", icon = R.drawable.ic_chat)
    object Home : Screen(route = "home", title = "Home", icon = R.drawable.ic_home)
    object Bookcase : Screen(route = "bookcase", title = "Bookcase", icon = R.drawable.ic_bookcase)
    object Profile : Screen(route = "profile", title = "Profile", icon = R.drawable.ic_profile)
}
