package com.warrantease.androidapp.presentation.ui.warranties

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ramcosta.composedestinations.annotation.Destination
import com.warrantease.androidapp.R
import com.warrantease.androidapp.presentation.ui.components.BottomNav
import com.warrantease.androidapp.presentation.ui.components.WarrantPreview
import com.warrantease.androidapp.presentation.ui.theme.AppTheme
import com.warrantease.androidapp.presentation.viewmodel.WarrantyViewModel
import com.warrantease.androidapp.presentation.viewmodel.uiState.UIState
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun WarrantiesScreen(
	navController: NavController,
	viewModel: WarrantyViewModel = koinViewModel(),
) {
	viewModel.getWarranties()
	val warranties by viewModel.warranties.collectAsState()
	val uiState by viewModel.state.collectAsState()
	val user = Firebase.auth.currentUser!!

	Content(navController = navController, user = user) {
		when (uiState) {
			UIState.NORMAL -> {
				LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
					items(items = warranties) { warranty ->
						WarrantPreview(warranty)
					}
				}
			}

			UIState.EMPTY -> {
				Text(text = "Empty")
			}

			UIState.LOADING -> {
				Text(text = "Loading")
			}

			UIState.ERROR -> {
				Text(text = "Error")
			}
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
	navController: NavController,
	user: FirebaseUser,
	content: @Composable (() -> Unit),
) {
	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = { Text(text = "Warranties") },
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
			)
		) {
			Card(modifier = Modifier
				.fillMaxWidth(),
				colors = CardDefaults.cardColors(
					containerColor = AppTheme.neutral100,
				),
				shape = RoundedCornerShape(35.dp),
				onClick = { /* TODO open sheet */ }
			) {
				Row(
					modifier = Modifier
						.padding(15.dp)
						.fillMaxWidth(),
					verticalAlignment = Alignment.CenterVertically,
					horizontalArrangement = Arrangement.SpaceBetween
				) {
					Text(
						text = stringResource(R.string.search_bar_warranty_hint),
						color = AppTheme.neutral500
					)
					Icon(imageVector = Icons.Default.Search, contentDescription = "search")
				}
			}

			Spacer(modifier = Modifier.height(20.dp))

			content()
		}
	}
}
