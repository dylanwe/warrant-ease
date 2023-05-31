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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.warrantease.androidapp.R
import com.warrantease.androidapp.auth.EmailAuth
import com.warrantease.androidapp.auth.GoogleAuth
import com.warrantease.androidapp.presentation.ui.components.EmailForm
import com.warrantease.androidapp.presentation.ui.destinations.SignUpScreenDestination

@RootNavGraph(start = true)
@Destination
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SignInScreen(navigator: DestinationsNavigator) {
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
                    text = stringResource(R.string.sign_in),
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    EmailForm(submitLabel = stringResource(id = R.string.sign_in), onSubmit = emailAuth::signIn)
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
                    Text(text = stringResource(R.string.or))
                    Divider(
                        color = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                GoogleSignInButton(
                    text = stringResource(R.string.sign_in_with_google),
                    loadingText = stringResource(R.string.signing_in),
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
                Text(text = stringResource(R.string.don_t_have_an_account))
                Text(
                    text = stringResource(R.string.sign_up),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable {
                        navigator.navigate(SignUpScreenDestination)
                    }
                )
            }
        }
    }
}
