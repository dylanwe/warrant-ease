package com.example.androidapp

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androidapp.auth.GoogleSignInActivity
import com.example.androidapp.ui.Screens
import com.example.androidapp.ui.home.HomeScreen
import com.example.androidapp.ui.sign_in.SignInScreen
import com.example.androidapp.ui.theme.AndroidAppTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : GoogleSignInActivity() {
    override fun onAuthStateChanged(p0: FirebaseAuth) {
        setContent {
            AndroidAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navHostController = rememberNavController()
                    AppNavigation(
                        navHostController = navHostController,
                        signIn = { super.signIn() }
                    )
                }
            }
        }
    }
}

@Composable
fun AppNavigation(
    navHostController: NavHostController,
    signIn: () -> Unit,
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
            SignInScreen(signIn = signIn)
        }
    }
}
