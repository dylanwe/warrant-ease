package com.warrantease.androidapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.warrantease.androidapp.ui.Screens
import com.warrantease.androidapp.ui.home.HomeScreen
import com.warrantease.androidapp.ui.sign_in.SignInScreen
import com.warrantease.androidapp.ui.sign_up.SignUpScreen
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
            SignInScreen(navController = navHostController)
        }
        composable(Screens.SignUpScreen.route) {
            SignUpScreen(navController = navHostController)
        }
    }
}
