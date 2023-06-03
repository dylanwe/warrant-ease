package com.warrantease.androidapp.presentation.ui.warranty.edit

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import com.warrantease.androidapp.presentation.ui.components.WarrantyDatePicker
import com.warrantease.androidapp.presentation.ui.theme.AppTheme
import com.warrantease.androidapp.presentation.ui.utils.WarrantyDateFormatter.dateFormatter
import com.warrantease.androidapp.presentation.viewmodel.editWarranty.EditWarrantyViewModel
import com.warrantease.androidapp.presentation.viewmodel.editWarranty.model.EditWarrantyViewModelArgs
import com.warrantease.androidapp.presentation.viewmodel.uiState.UIState
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.time.LocalDate

@Destination
@Composable
fun EditWarrantyScreen(
	warrantyId: Long,
	navController: NavController,
	viewModel: EditWarrantyViewModel = koinViewModel(
		parameters = {
			parametersOf(EditWarrantyViewModelArgs(warrantyId = warrantyId))
		}
	),
) {
	val updateWarrantyState by viewModel.updateWarrantyState.collectAsState()
	val uiState by viewModel.uiState.collectAsState()
	val warranty by viewModel.warranty.collectAsState()
	val context = LocalContext.current

	when (uiState) {
		UIState.NORMAL -> {
			warranty?.let {
				Content(navController = navController, viewModel = viewModel, warranty = it)
			}

			when (updateWarrantyState) {
				UIState.NORMAL -> {
					Toast.makeText(
						context,
						context.getString(R.string.updated_warranty),
						Toast.LENGTH_SHORT
					).show()

					navController.popBackStack()
				}

				UIState.ERROR -> {
					Toast.makeText(
						context,
						context.getString(R.string.warranty_update_error),
						Toast.LENGTH_SHORT
					).show()
				}

				else -> {}
			}
		}

		UIState.LOADING -> {
			Column(Modifier.fillMaxSize()) {
				CircularProgressIndicator()
			}
		}

		UIState.ERROR -> {
			Toast.makeText(
				context,
				context.getString(R.string.warranty_update_error),
				Toast.LENGTH_SHORT
			).show()

			navController.popBackStack()
		}

		else -> {
			// Do nothing
		}
	}

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
	warranty: Warranty,
	navController: NavController,
	viewModel: EditWarrantyViewModel,
) {
	var name by remember {
		mutableStateOf(TextFieldValue(warranty.name))
	}
	var store by remember {
		mutableStateOf(TextFieldValue(warranty.store))
	}
	var buyDate by remember {
		mutableStateOf<LocalDate?>(warranty.buyDate)
	}
	var isBuyDateDialogOpen by remember { mutableStateOf(false) }
	var expiration by remember {
		mutableStateOf<LocalDate?>(warranty.expirationDate)
	}
	var isExpirationDateDialogOpen by remember { mutableStateOf(false) }

	var notes by remember {
		mutableStateOf(TextFieldValue(warranty.notes))
	}

	val context = LocalContext.current

	WarrantyDatePicker(
		isDialogOpen = isBuyDateDialogOpen,
		setDate = { buyDate = it },
		setDialogState = { isBuyDateDialogOpen = it },
		initialDate = warranty.buyDate
	)
	WarrantyDatePicker(
		isDialogOpen = isExpirationDateDialogOpen,
		setDate = { expiration = it },
		setDialogState = { isExpirationDateDialogOpen = it },
		initialDate = warranty.expirationDate
	)
	Scaffold(
		topBar = {
			TopAppBar(
				title = { Text(text = stringResource(id = R.string.edit_warranty_title)) },
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
						value = buyDate?.format(dateFormatter) ?: "",
						onValueChange = {},
						shape = RoundedCornerShape(18.dp),
						enabled = false,
						modifier = Modifier
							.weight(0.5f)
							.clickable { isBuyDateDialogOpen = true },
						trailingIcon = {
							Icon(
								painter = painterResource(id = R.drawable.baseline_calendar_month_24),
								contentDescription = "buy date",
								tint = AppTheme.neutral500
							)
						},
						colors = TextFieldDefaults.outlinedTextFieldColors(
							disabledTextColor = MaterialTheme.colorScheme.onSurface,
							disabledBorderColor = MaterialTheme.colorScheme.outline,
							disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
							disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
							// For Icons
							disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
							disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
						)
					)
					OutlinedTextField(
						label = { Text(text = stringResource(id = R.string.add_warranty_expiration_date)) },
						placeholder = { Text(text = stringResource(id = R.string.add_warranty_date_hint)) },
						value = expiration?.format(dateFormatter) ?: "",
						onValueChange = {},
						shape = RoundedCornerShape(18.dp),
						enabled = false,
						modifier = Modifier
							.weight(0.5f)
							.clickable { isExpirationDateDialogOpen = true },
						trailingIcon = {
							Icon(
								painter = painterResource(id = R.drawable.baseline_calendar_month_24),
								contentDescription = "expiration date",
								tint = AppTheme.neutral500
							)
						},
						colors = TextFieldDefaults.outlinedTextFieldColors(
							disabledTextColor = MaterialTheme.colorScheme.onSurface,
							disabledBorderColor = MaterialTheme.colorScheme.outline,
							disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
							disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
							// For Icons
							disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
							disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
						)
					)
				}

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
				text = stringResource(R.string.update_button),
				onClick = {
					val areInputsValid = areInputsValid(
						context = context,
						name = name.text,
						buy = buyDate,
						expiration = expiration,
					)
					if (areInputsValid && buyDate != null && expiration != null) {
						val updateWarranty = Warranty(
							id = warranty.id,
							name = name.text,
							store = store.text,
							notes = notes.text,
							buyDate = buyDate!!,
							expirationDate = expiration!!
						)

						viewModel.updateWarranty(updateWarranty)
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
	buy: LocalDate?,
	expiration: LocalDate?,
): Boolean {
	if (name.isBlank()) {
		Toast.makeText(
			context,
			context.getString(R.string.name_should_not_be_empty),
			Toast.LENGTH_SHORT
		).show()
		return false
	}

	if (buy == null || expiration == null) {
		Toast.makeText(
			context,
			context.getString(R.string.add_warranty_date_incorrect),
			Toast.LENGTH_SHORT
		).show()
		return false
	}

	if (expiration < buy) {
		Toast.makeText(
			context,
			context.getString(R.string.add_warranty_expiration_smaller),
			Toast.LENGTH_SHORT
		).show()
		return false
	}

	return true
}