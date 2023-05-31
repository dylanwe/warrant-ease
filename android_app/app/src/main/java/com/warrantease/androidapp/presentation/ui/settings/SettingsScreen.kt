package com.warrantease.androidapp.presentation.ui.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ramcosta.composedestinations.annotation.Destination
import com.warrantease.androidapp.R
import com.warrantease.androidapp.presentation.ui.components.BottomNav
import com.warrantease.androidapp.presentation.ui.theme.AppTheme
import com.warrantease.androidapp.presentation.viewmodel.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Destination
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
	navController: NavController,
	viewModel: HomeViewModel = koinViewModel(),
) {
	viewModel.getTopWarranties()

	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = { Text(text = stringResource(id = R.string.settings_title)) },
				colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
					containerColor = AppTheme.white
				)
			)
		},
		bottomBar = { BottomNav(navController) }
	) { innerPadding ->
		Column(
			modifier = Modifier.padding(
				top = innerPadding.calculateTopPadding(),
				bottom = innerPadding.calculateBottomPadding(),
				start = 18.dp,
				end = 18.dp
			),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Center
		) {
			Image(
				painter = painterResource(id = R.drawable.settings_image),
				contentDescription = "Settings"
			)

			Spacer(modifier = Modifier.height(10.dp))

			Button(onClick = {
				Firebase.auth.signOut()
			}) {
				Text(text = stringResource(R.string.sign_out_text))
			}
		}
	}
}
