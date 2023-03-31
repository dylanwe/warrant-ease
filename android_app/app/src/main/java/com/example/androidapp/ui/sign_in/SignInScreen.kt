package com.example.androidapp.ui.sign_in

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.androidapp.R

@Composable
fun SignInScreen(
    googleSignIn: () -> Unit,
    emailSignIn: (String, String) -> Unit,
    emailSignUp: (String, String) -> Unit
) {
    var text by remember { mutableStateOf<String?>(null) }
    AuthView(
        errorText = text,
        googleSignIn = {
            text = null
            googleSignIn()
        },
        emailSignIn = { email, password ->
            text = null
            emailSignIn(email, password)
        },
        emailSignUp = { email, password ->
            text = null
            emailSignUp(email, password)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AuthView(
    errorText: String?,
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

            errorText?.let {
                isGoogleLoading = false
                Spacer(modifier = Modifier.height(30.dp))
                Text(text = it)
            }
        }
    }
}
