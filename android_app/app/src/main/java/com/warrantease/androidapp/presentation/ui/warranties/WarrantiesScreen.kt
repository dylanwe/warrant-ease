package com.warrantease.androidapp.presentation.ui.warranties

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ramcosta.composedestinations.annotation.Destination
import com.warrantease.androidapp.presentation.ui.components.BottomNav
import com.warrantease.androidapp.presentation.viewmodel.WarrantyViewModel
import org.koin.androidx.compose.koinViewModel

@Destination
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WarrantiesScreen(
    navController: NavController,
    viewModel: WarrantyViewModel = koinViewModel(),
) {
	viewModel.getTopWarranties()
	val user = Firebase.auth.currentUser!!

	Scaffold(
		bottomBar = { BottomNav(navController) }
	) { innerPadding ->
		Column(
			modifier = Modifier.padding(
				top = innerPadding.calculateTopPadding(),
				bottom = innerPadding.calculateBottomPadding(),
				start = 18.dp,
				end = 18.dp
			)
		) {
			Text(
				text = "Hello, ${user.displayName}",
				fontWeight = FontWeight.Bold,
				style = MaterialTheme.typography.titleMedium,
				fontSize = 30.sp
			)
			Text(text = "Warranties")

			Spacer(modifier = Modifier.height(10.dp))
			Text(text = user.email ?: "")
			Spacer(modifier = Modifier.height(10.dp))
		}
	}
}
