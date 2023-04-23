package com.warrantease.androidapp.presentation.ui.sign_in

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.clickable
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
import androidx.navigation.NavController
import com.warrantease.androidapp.R
import com.warrantease.androidapp.auth.EmailAuth
import com.warrantease.androidapp.auth.GoogleAuth
import com.warrantease.androidapp.presentation.ui.Screens
import com.warrantease.androidapp.presentation.ui.components.EmailForm
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SignInScreen(navController: NavController) {
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
                    EmailForm(submitLabel = "Sign in", onSubmit = emailAuth::signIn)
                }

                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Divider(
                        color = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.weight(1f)
                    )
                    Text(text = "Or")
                    Divider(
                        color = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.weight(1f)
                    )
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
                Text(text = "Don’t have an account?")
                Text(
                    text = "Sign up",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable {
                        navController.navigate(Screens.SignUpScreen.route)
                    }
                )
            }
        }
    }
}
