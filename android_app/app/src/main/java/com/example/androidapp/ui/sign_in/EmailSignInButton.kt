package com.example.androidapp.ui.sign_in

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.androidapp.R

@Composable
fun EmailSignInButton(
    emailSignIn: (String, String) -> Unit,
    emailSignUp: (String, String) -> Unit
) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = email,
            label = { Text(text = "Email") },
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        OutlinedTextField(
            value = password,
            label = { Text(text = "Password") },
            onValueChange = { password = it },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (isPasswordVisible)
                    R.drawable.baseline_visibility_24
                else R.drawable.baseline_visibility_off_24

                // TODO: Please provide localized description for accessibility services
                val description = if (isPasswordVisible) {
                    stringResource(id = R.string.hide_password)
                } else {
                    stringResource(id = R.string.show_password)
                }

                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(painter = painterResource(id = image), description)
                }
            }
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                try {
                    emailSignIn(email, password)
                } catch (e: Exception) {
                    informUser("Sign in went wrong", context)
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(text = "Sign in")
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                try {
                    emailSignUp(email, password)
                } catch (e: Exception) {
                    informUser("Sign up went wrong", context)
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Text(text = "Sign up")
        }
    }
}

fun informUser(message: String, context: Context) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}