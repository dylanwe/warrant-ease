package com.warrantease.androidapp.presentation.ui.onboarding

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.warrantease.androidapp.R
import com.warrantease.androidapp.presentation.ui.components.GradientButton
import com.warrantease.androidapp.presentation.ui.destinations.HomeScreenDestination
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

@Destination
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun OnboardingScreen(navigator: DestinationsNavigator) {
    var usernameInput by remember { mutableStateOf("") }
    val context = LocalContext.current

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(38.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.username_illustration),
                        contentDescription = "sign up illustration",
                        modifier = Modifier.width(220.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.username_title),
                        style = MaterialTheme.typography.headlineLarge,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                    )

                    Text(
                        text = stringResource(id = R.string.username_subtitle),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                OutlinedTextField(
                    value = usernameInput,
                    label = { Text(text = stringResource(id = R.string.username_label)) },
                    onValueChange = { usernameInput = it },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_account_circle_24),
                            contentDescription = "account"
                        )
                    },
                    placeholder = { Text(text = stringResource(id = R.string.username_placeholder)) },
                    shape = RoundedCornerShape(18.dp),
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                )
                GradientButton(
                    text = stringResource(id = R.string.continue_cta),
                    onClick = {
                        // Validate username
                        if (isUsernameInvalid(usernameInput)) {
                            informUser(context, R.string.username_short_error)
                            return@GradientButton
                        }

                        // Update the users username 
                        runBlocking {
                            Firebase.auth.currentUser!!.updateProfile(
                                userProfileChangeRequest {
                                    displayName = usernameInput
                                }
                            ).await()
                        }

                        if (Firebase.auth.currentUser!!.displayName.isNullOrBlank()) {
                            informUser(context, R.string.username_error)
                        } else {
                            navigator.navigate(HomeScreenDestination)
                        }
                    }
                )
            }
        }
    }
}

private fun isUsernameInvalid(username: String): Boolean {
    return username.isBlank() || username.isEmpty() || username.length < 2
}

private fun informUser(context: Context, stringRes: Int) {
    Toast.makeText(context, context.getText(stringRes), Toast.LENGTH_SHORT).show()
}