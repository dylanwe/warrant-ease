package com.warrantease.androidapp.presentation.ui.sign_up

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.warrantease.androidapp.R
import com.warrantease.androidapp.auth.EmailAuth
import com.warrantease.androidapp.presentation.ui.components.EmailForm
import com.warrantease.androidapp.presentation.ui.destinations.SignInScreenDestination

@Destination
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SignUpScreen(navigator: DestinationsNavigator) {
	val auth = Firebase.auth
	val emailAuth = EmailAuth(auth)

	Scaffold {
		Column(
			modifier = Modifier
                .fillMaxSize()
                .padding(38.dp),
			verticalArrangement = Arrangement.SpaceBetween,
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
				Image(
					painter = painterResource(id = R.drawable.sign_up_illustration),
					contentDescription = "sign up illustration"
				)
				Text(
					text = stringResource(id = R.string.sign_up),
					style = MaterialTheme.typography.headlineLarge,
					textAlign = TextAlign.Center,
					fontWeight = FontWeight.Bold,
					modifier = Modifier.fillMaxWidth(),
				)

				Column(
					modifier = Modifier
						.fillMaxWidth()
				) {
					EmailForm(
						submitLabel = stringResource(id = R.string.sign_up),
						onSubmit = emailAuth::signUp
					)
				}
			}

			Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
				Text(text = stringResource(R.string.already_have_an_account))
				Text(
					text = stringResource(id = R.string.sign_in),
					color = MaterialTheme.colorScheme.primary,
					modifier = Modifier.clickable {
						navigator.navigate(SignInScreenDestination)
					}
				)
			}
		}
	}
}
