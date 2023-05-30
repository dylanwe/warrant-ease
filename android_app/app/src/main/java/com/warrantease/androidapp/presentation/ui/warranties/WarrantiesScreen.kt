package com.warrantease.androidapp.presentation.ui.warranties

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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
	focusSearchField: Boolean = false,
	navController: NavController,
	viewModel: WarrantyViewModel = koinViewModel(),
) {
	viewModel.getWarranties()
	val warranties by viewModel.warranties.collectAsState()
	val uiState by viewModel.state.collectAsState()

	Content(
		viewModel = viewModel,
		navController = navController,
		focusSearchField = focusSearchField
	) {
		when (uiState) {
			UIState.NORMAL -> {
				LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
					items(items = warranties) { warranty ->
						WarrantPreview(warranty, navController)
					}
				}
			}

			UIState.EMPTY -> {
				Column(
					modifier = Modifier.fillMaxSize(),
					horizontalAlignment = Alignment.CenterHorizontally,
					verticalArrangement = Arrangement.Center
				) {
					Image(
						painter = painterResource(id = R.drawable.state_empty),
						contentDescription = "empty"
					)
					Text(
						text = stringResource(R.string.state_message_no_warranties),
						textAlign = TextAlign.Center,
						color = AppTheme.neutral600
					)
				}
			}

			UIState.LOADING -> {
				Column(
					modifier = Modifier.fillMaxSize(),
					horizontalAlignment = Alignment.CenterHorizontally,
					verticalArrangement = Arrangement.Center
				) {
					CircularProgressIndicator()
				}
			}

			UIState.ERROR -> {
				Column(
					modifier = Modifier.fillMaxSize(),
					horizontalAlignment = Alignment.CenterHorizontally,
					verticalArrangement = Arrangement.Center
				) {
					Image(
						painter = painterResource(id = R.drawable.state_error),
						contentDescription = "empty"
					)
					Text(
						text = stringResource(R.string.state_message_error),
						textAlign = TextAlign.Center,
						color = AppTheme.neutral600
					)
				}
			}
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
	viewModel: WarrantyViewModel,
	navController: NavController,
	focusSearchField: Boolean,
	content: @Composable (() -> Unit),
) {
	val focusRequester = remember { FocusRequester() }
	var searchQuery by remember {
		mutableStateOf(TextFieldValue(String()))
	}

	if (focusSearchField) {
		LaunchedEffect(Unit) {
			focusRequester.requestFocus()
		}
	}

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
			modifier = Modifier
				.padding(
					top = innerPadding.calculateTopPadding(),
					bottom = innerPadding.calculateBottomPadding(),
					start = 18.dp,
					end = 18.dp
				)
				.focusRequester(focusRequester)
		) {
			TextField(
				modifier = Modifier
					.fillMaxWidth()
					.padding(bottom = 8.dp),
				value = searchQuery,
				placeholder = {
					Text(
						text = stringResource(id = R.string.search_bar_warranty_hint),
						color = AppTheme.neutral500
					)
				},
				colors = TextFieldDefaults.textFieldColors(
					containerColor = AppTheme.neutral100,
					cursorColor = Color.Black,
					disabledLabelColor = AppTheme.white,
					focusedIndicatorColor = Color.Transparent,
					unfocusedIndicatorColor = Color.Transparent
				),
				onValueChange = {
					searchQuery = it
					viewModel.getWarranties(searchQuery.text)
				},
				shape = RoundedCornerShape(35.dp),
				singleLine = true,
				trailingIcon = {
					if (searchQuery.text.isNotEmpty()) {
						IconButton(onClick = { searchQuery = TextFieldValue(String()) }) {
							Icon(
								imageVector = Icons.Outlined.Close,
								contentDescription = "clear text-field"
							)
						}
					} else {
						IconButton(
							modifier = Modifier.padding(end = 6.dp),
							onClick = { /*Do nothing*/ }) {
							Icon(
								imageVector = Icons.Default.Search,
								contentDescription = "search"
							)
						}
					}
				}
			)

			Spacer(modifier = Modifier.height(20.dp))

			content()
		}
	}
}
