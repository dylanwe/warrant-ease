package com.example.androidapp.ui.sign_in

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.androidapp.R
import com.example.androidapp.auth.EmailAuth
import com.example.androidapp.auth.GoogleAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SignInScreen() {
    val auth = Firebase.auth
    val googleAuth = GoogleAuth(auth, LocalContext.current as Activity)
    val emailAuth = EmailAuth(auth)

    var isGoogleLoading by remember { mutableStateOf(false) }

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(50.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                EmailSignInButton(
                    emailSignIn = emailAuth::signIn,
                    emailSignUp = emailAuth::signUp
                )
            }

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
                    googleAuth.signIn()
                }
            )
        }
    }
}
