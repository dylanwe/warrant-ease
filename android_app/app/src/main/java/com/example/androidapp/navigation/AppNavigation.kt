package com.example.androidapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.androidapp.ui.Screens
import com.example.androidapp.ui.home.HomeScreen
import com.example.androidapp.ui.sign_in.SignInScreen
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun AppNavigation(
    navHostController: NavHostController,
    googleSignIn: () -> Unit,
    emailSignIn: (String, String) -> Unit,
    emailSignUp: (String, String) -> Unit
) {
    val startScreen = if (Firebase.auth.currentUser == null) {
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
            SignInScreen(
                googleSignIn = googleSignIn,
                emailSignIn = emailSignIn,
                emailSignUp = emailSignUp
            )
        }
    }
}
