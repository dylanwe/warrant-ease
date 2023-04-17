package com.example.androidapp.ui.sign_in

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
                .padding(38.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    text = "Sign in",
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    EmailSignInForm(
                        emailSignIn = emailAuth::signIn,
                        emailSignUp = emailAuth::signUp
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Divider(color = MaterialTheme.colorScheme.outline, modifier = Modifier.weight(1f))
                    Text(text = "Or")
                    Divider(color = MaterialTheme.colorScheme.outline, modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(8.dp))

                GoogleSignInButton(
                    text = "Sign in with Google",
                    loadingText = "Signing in...",
                    isLoading = isGoogleLoading,
                    icon = painterResource(id = R.drawable.ic_google_logo),
                    shape = RoundedCornerShape(18.dp),
                    googleSignIn = {
                        isGoogleLoading = true
                        googleAuth.signIn()
                    }
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(text = "Already have an account?")
                Text(text = "Sign Up", color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}
