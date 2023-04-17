package com.example.androidapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.androidapp.ui.Screens
import com.example.androidapp.ui.home.HomeScreen
import com.example.androidapp.ui.sign_in.SignInScreen
import com.google.firebase.auth.FirebaseUser

@Composable
fun AppNavigation(
    navHostController: NavHostController,
    firebaseUser: FirebaseUser?
) {
    // TODO: If user doesn't have a display show him the onboard screen to create one
    val startScreen = if (firebaseUser == null) {
        Screens.SignInScreen.route
    } else {
        Screens.HomeScreen.route
    }

    NavHost(
        navController = navHostController,
        startDestination = startScreen
    ) {
        composable(Screens.HomeScreen.route) {
            HomeScreen()
        }
        composable(Screens.SignInScreen.route) {
            SignInScreen()
        }
    }
}
