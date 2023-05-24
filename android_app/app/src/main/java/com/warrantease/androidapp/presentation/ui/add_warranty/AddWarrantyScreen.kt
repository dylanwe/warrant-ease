package com.warrantease.androidapp.presentation.ui.add_warranty

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.warrantease.androidapp.R
import com.warrantease.androidapp.domain.model.Warranty
import com.warrantease.androidapp.presentation.ui.components.BottomNav
import com.warrantease.androidapp.presentation.ui.components.GradientButton
import com.warrantease.androidapp.presentation.ui.theme.AppTheme
import com.warrantease.androidapp.presentation.viewmodel.AddWarrantyViewModel
import com.warrantease.androidapp.presentation.viewmodel.uiState.UIState
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Destination
@Composable
fun AddWarrantyScreen(
	navController: NavController,
	viewModel: AddWarrantyViewModel = koinViewModel(),
) {
	val uiState by viewModel.saveWarrantyState.collectAsState()
	val context = LocalContext.current
	Content(navController = navController, viewModel = viewModel)

	when (uiState) {
		UIState.NORMAL -> {
			Toast.makeText(
				context,
				context.getString(R.string.saved_warranty),
				Toast.LENGTH_SHORT
			).show()

			navController.popBackStack()
		}

		UIState.ERROR -> {
			Toast.makeText(
				context,
				context.getString(R.string.warranty_save_error),
				Toast.LENGTH_SHORT
			).show()
		}

		else -> {}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
	navController: NavController,
	viewModel: AddWarrantyViewModel,
) {
	var name by remember {
		mutableStateOf(TextFieldValue(""))
	}
	var store by remember {
		mutableStateOf(TextFieldValue(""))
	}
	var buyDate by remember {
		mutableStateOf(TextFieldValue(""))
	}
	var expiration by remember {
		mutableStateOf(TextFieldValue(""))
	}
	// var reminder by remember {
	// 	mutableStateOf(TextFieldValue(""))
	// }
	var notes by remember {
		mutableStateOf(TextFieldValue(""))
	}

	val context = LocalContext.current

	Scaffold(
		topBar = {
			TopAppBar(
				title = { Text(text = stringResource(id = R.string.add_warranty_title)) },
				navigationIcon = {
					IconButton(onClick = { navController.popBackStack() }) {
						Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
					}
				},
				colors = TopAppBarDefaults.largeTopAppBarColors(
					containerColor = AppTheme.white
				)
			)
		},
		bottomBar = { BottomNav(navController = navController) }
	) { innerPadding ->
		Column(
			verticalArrangement = Arrangement.SpaceBetween,
			modifier = Modifier
				.fillMaxSize()
				.padding(
					top = innerPadding.calculateTopPadding(),
					bottom = innerPadding
						.calculateBottomPadding()
						.plus(18.dp),
					start = 18.dp,
					end = 18.dp
				),
		) {
			Column(
				verticalArrangement = Arrangement.spacedBy(16.dp)
			) {

				OutlinedTextField(
					label = { Text(text = stringResource(id = R.string.add_warranty_name)) },
					placeholder = { Text(text = stringResource(id = R.string.add_warranty_name_hint)) },
					value = name.text,
					onValueChange = { name = TextFieldValue(it) },
					shape = RoundedCornerShape(18.dp),
					modifier = Modifier.fillMaxWidth(),
					maxLines = 1,
					singleLine = true
				)

				OutlinedTextField(
					label = { Text(text = stringResource(id = R.string.add_warranty_store)) },
					placeholder = { Text(text = stringResource(id = R.string.add_warranty_store_hint)) },
					value = store.text,
					onValueChange = { store = TextFieldValue(it) },
					shape = RoundedCornerShape(18.dp),
					modifier = Modifier.fillMaxWidth(),
					maxLines = 1,
					singleLine = true
				)

				Row(
					horizontalArrangement = Arrangement.spacedBy(16.dp)
				) {
					OutlinedTextField(
						label = { Text(text = stringResource(id = R.string.add_warranty_buy_date)) },
						placeholder = { Text(text = stringResource(id = R.string.add_warranty_date_hint)) },
						value = buyDate.text,
						onValueChange = { buyDate = TextFieldValue(it) },
						shape = RoundedCornerShape(18.dp),
						modifier = Modifier.weight(0.5f),
						trailingIcon = {
							Icon(
								painter = painterResource(id = R.drawable.baseline_calendar_month_24),
								contentDescription = "buy date",
								tint = AppTheme.neutral500
							)
						}
					)
					OutlinedTextField(
						label = { Text(text = stringResource(id = R.string.add_warranty_expiration_date)) },
						placeholder = { Text(text = stringResource(id = R.string.add_warranty_date_hint)) },
						value = expiration.text,
						onValueChange = { expiration = TextFieldValue(it) },
						shape = RoundedCornerShape(18.dp),
						modifier = Modifier.weight(0.5f),
						trailingIcon = {
							Icon(
								painter = painterResource(id = R.drawable.baseline_calendar_month_24),
								contentDescription = "expiration date",
								tint = AppTheme.neutral500
							)
						}
					)
				}

				// OutlinedTextField(
				// 	label = { Text(text = "Reminder") },
				// 	placeholder = { Text(text = "13-07-2020") },
				// 	value = reminder.text,
				// 	onValueChange = { reminder = TextFieldValue(it) },
				// 	shape = RoundedCornerShape(18.dp),
				// 	modifier = Modifier.fillMaxWidth(),
				// 	trailingIcon = {
				// 		Icon(
				// 			painter = painterResource(id = R.drawable.outline_notifications_24),
				// 			contentDescription = "reminder",
				// 			tint = AppTheme.neutral500
				// 		)
				// 	}
				// )

				OutlinedTextField(
					label = { Text(text = stringResource(id = R.string.add_warranty_notes)) },
					placeholder = { Text(text = "...") },
					value = notes.text,
					onValueChange = { notes = TextFieldValue(it) },
					shape = RoundedCornerShape(18.dp),
					modifier = Modifier.fillMaxWidth(),
					maxLines = 4
				)
			}
			GradientButton(
				text = "Save",
				onClick = {
					val areInputsValid = areInputsValid(
						context = context,
						name = name.text,
						buy = buyDate.text,
						expiration = expiration.text,
						// reminder = reminder.text,
						notes = notes.text
					)
					if (areInputsValid) {
						val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

						val warranty = Warranty(
							id = -1,
							name = name.text,
							store = store.text,
							notes = notes.text,
							buyDate = LocalDate.parse(buyDate.text, formatter),
							expirationDate = LocalDate.parse(expiration.text, formatter),
							reminderDate = LocalDate.now()
							// reminderDate = LocalDate.parse(reminder.text, formatter)
						)

						viewModel.saveWarranty(warranty)
					}
				}
			)
		}
	}
}

/**
 * Checks if inputs are valid and shows in toast what is invalid
 */
private fun areInputsValid(
	context: Context,
	name: String,
	buy: String,
	expiration: String,
	// reminder: String,
	notes: String,
): Boolean {
	val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

	if (name.isBlank()) {
		Toast.makeText(
			context,
			context.getString(R.string.name_should_not_be_empty),
			Toast.LENGTH_SHORT
		).show()
		return false
	}

	try {
		LocalDate.parse(buy, formatter)
		LocalDate.parse(expiration, formatter)
		// LocalDate.parse(reminder, formatter)
	} catch (e: Exception) {
		Toast.makeText(
			context,
			context.getString(R.string.could_not_parse_dates),
			Toast.LENGTH_SHORT
		).show()
		return false
	}

	return true
}