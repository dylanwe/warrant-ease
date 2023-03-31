package com.example.androidapp

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.androidapp.auth.SignInActivity
import com.example.androidapp.navigation.AppNavigation
import com.example.androidapp.ui.theme.AndroidAppTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : SignInActivity() {
    override fun onAuthStateChanged(firebaseAuth: FirebaseAuth) {
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
                        googleSignIn = { super.googleSignIn() },
                        emailSignIn = { email, password -> super.emailSignIn(email, password) },
                        emailSignUp = { email, password -> super.emailSignUp(email, password) }
                    )
                }
            }
        }
    }
}
