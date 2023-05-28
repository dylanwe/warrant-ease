package com.warrantease.androidapp.presentation.ui.home

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigate
import com.warrantease.androidapp.R
import com.warrantease.androidapp.presentation.ui.components.BottomNav
import com.warrantease.androidapp.presentation.ui.components.GradientFloatingActionButton
import com.warrantease.androidapp.presentation.ui.components.WarrantPreview
import com.warrantease.androidapp.presentation.ui.destinations.AddWarrantyScreenDestination
import com.warrantease.androidapp.presentation.ui.destinations.WarrantiesScreenDestination
import com.warrantease.androidapp.presentation.ui.theme.AppTheme
import com.warrantease.androidapp.presentation.viewmodel.HomeViewModel
import com.warrantease.androidapp.presentation.viewmodel.uiState.UIState
import org.koin.androidx.compose.koinViewModel
import java.util.Calendar


@Destination
@Composable
fun HomeScreen(
	navController: NavController,
	viewModel: HomeViewModel = koinViewModel(),
) {
	viewModel.getTopWarranties()
	val warranties by viewModel.topWarranties.collectAsState()
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
	val context = LocalContext.current

	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = { Text(text = "Home") },
				colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
					containerColor = AppTheme.white
				)
			)
		},
		bottomBar = { BottomNav(navController) },
		floatingActionButton = {
			GradientFloatingActionButton(
				icon = Icons.Default.Add,
				onClick = {
					navController.navigate(AddWarrantyScreenDestination)
				}
			)
		}
	) { innerPadding ->
		Column(
			modifier = Modifier.padding(
				top = innerPadding.calculateTopPadding(),
				bottom = innerPadding.calculateBottomPadding(),
				start = 18.dp,
				end = 18.dp
			)
		) {
			Row(
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.spacedBy(4.dp)
			) {
				Icon(
					painter = painterResource(id = R.drawable.outline_wb_sunny_24),
					contentDescription = "time icon",
					tint = AppTheme.neutral500,
					modifier = Modifier.size(18.dp)
				)
				Text(
					text = "${greetingMessage(context)},",
					fontSize = 16.sp,
					color = AppTheme.neutral500
				)
			}

			Text(
				text = "${user.displayName}",
				fontWeight = FontWeight.Bold,
				fontSize = 24.sp
			)

			Spacer(modifier = Modifier.height(20.dp))

			Card(modifier = Modifier
				.fillMaxWidth(),
				colors = CardDefaults.cardColors(
					containerColor = AppTheme.neutral100,
				),
				shape = RoundedCornerShape(35.dp),
				onClick = {
					navController.navigate(
						WarrantiesScreenDestination(focusSearchField = true)
					)
				}
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

			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceBetween,
				verticalAlignment = Alignment.Bottom
			) {
				Text(
					text = stringResource(id = R.string.upcoming_expirations),
					fontWeight = FontWeight.Bold,
					fontSize = 24.sp
				)
				Text(
					text = stringResource(id = R.string.see_all),
					color = AppTheme.primary400,
					fontWeight = FontWeight.Bold,
					modifier = Modifier.clickable {
						navController.navigate(
							WarrantiesScreenDestination()
						)
					}
				)
			}

			Spacer(modifier = Modifier.height(12.dp))

			content()
		}
	}
}

/**
 * Get greeting message based on time of day
 */
private fun greetingMessage(context: Context): String {
	val c: Calendar = Calendar.getInstance()

	return when (c.get(Calendar.HOUR_OF_DAY)) {
		in 0..11 -> {

			context.getString(R.string.welcome_good_morning)
		}

		in 12..15 -> {
			context.getString(R.string.welcome_good_afternoon)
		}

		in 16..20 -> {
			context.getString(R.string.welcome_good_evening)
		}

		else -> {
			context.getString(R.string.welcome_good_night)
		}
	}
}