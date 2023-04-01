package com.example.androidapp.ui.sign_in

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.androidapp.R

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SignInScreen(
    googleSignIn: () -> Unit,
    emailSignIn: (String, String) -> Unit,
    emailSignUp: (String, String) -> Unit
) {
    var isGoogleLoading by remember { mutableStateOf(false) }

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(50.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EmailSignInButton(
                emailSignIn = { email, password ->
                    emailSignIn(email, password)
                },
                emailSignUp = { email, password ->
                    emailSignUp(email, password)
                }
            )

            Spacer(modifier = Modifier.height(24.dp))
            Divider(color = Color.LightGray)
            Spacer(modifier = Modifier.height(24.dp))

            GoogleSignInButton(
                text = "Sign in with Google",
                loadingText = "Signing in...",
                isLoading = isGoogleLoading,
                icon = painterResource(id = R.drawable.ic_google_logo),
                googleSignIn = {
                    isGoogleLoading = true
                    googleSignIn()
                }
            )
        }
    }
}
