package com.example.androidapp.ui

sealed class Screens(val route: String) {
    object HomeScreen : Screens("home_screen")
    object SignInScreen : Screens("sign_in_screen")
    object SignUpScreen : Screens("sign_up_screen")
}
